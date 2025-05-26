package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import com.example.demo.dto.LoanApprovalRequest;
import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanDetailed;
import com.example.demo.entity.ApprovedLoan;
import com.example.demo.entity.Customers;
import com.example.demo.entity.Fee;
import com.example.demo.entity.InterestRate;
import com.example.demo.entity.LoanRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InterestTermRepository;
import com.example.demo.repository.LoanRequestRepository;

import jakarta.transaction.Transactional;

import com.example.demo.repository.ApprovedLoanRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.FeeRepository;

@Service
public class LoanRequestService {

    // ✅ Logger declarado aquí
    private static final Logger logger = LoggerFactory.getLogger(LoanRequestService.class);
    
    @Autowired
    private InterestTermRepository interestRepo;

    @Autowired
    private LoanRequestRepository requestRepo;

      @Autowired
    private ApprovedLoanRepository approvedLoanRepo;

     @Autowired
    private FeeRepository feeRepo;

    @Autowired
    private CustomerRepository customerRepository;

//👉 Este sirve para que el cliente registre una solicitud de préstamo.
    public LoanRequest createLoanRequest(LoanRequestDTO dto) {
        InterestRate term = interestRepo.findByMonthTerm(dto.getTerm())
            .orElseThrow(() -> new IllegalArgumentException("Plazo no válido"));

        
    // Buscar al cliente en la base de datos por su ID
    Customers cliente = customerRepository.findById(dto.getCustomerId())
        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));    

        LoanRequest request = new LoanRequest();
        request.setLoanType(dto.getLoanType());
        request.setAmount(dto.getAmount());
        request.setTerm(dto.getTerm());
        request.setCustomer(cliente);
        request.setStatus("Pendiente de Aprobación");

        return requestRepo.save(request);
    }
    //obtener tasa de interes
    private double obtainInterestRate(int term){
        return interestRepo.findByMonthTerm(term)
        .map(InterestRate::getInterestRate)
            .orElseThrow(() -> new IllegalArgumentException("Plazo no válido o sin tasa configurada"));
    }
 @Transactional
    public void approveOrReject(LoanApprovalRequest request) {
        LoanRequest solicitud = getPendingRequest(request.getIdRequest());

        if (!request.isApprove()) {
            handleRejection(solicitud, request.getRejectionMotive());
            return;
        }

        double tasa = obtainInterestRate(solicitud.getTerm()); // Se obtiene antes del cambio de estado

        solicitud.setStatus("Aprobada");
        requestRepo.save(solicitud);

        ApprovedLoan prestamo = new ApprovedLoan();
        prestamo.setCustomer(solicitud.getCustomer());
        prestamo.setApprovalDate(LocalDate.now());
        prestamo.setAmount(solicitud.getAmount());
        prestamo.setTerm(solicitud.getTerm());
        prestamo.setLoanType(solicitud.getLoanType());
        prestamo.setInteresRate(tasa);

        approvedLoanRepo.save(prestamo);
        generarCuotas(prestamo);

        logger.info("Préstamo aprobado: solicitudId={}, clienteId={}, monto={}, plazo={}, tasa={}",
                solicitud.getId(), solicitud.getCustomer().getId(), solicitud.getAmount(), solicitud.getTerm(), tasa);
    }

    private LoanRequest getPendingRequest(Long id) {
        LoanRequest solicitud = requestRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        if (!"Pendiente de Aprobación".equals(solicitud.getStatus())) {
            throw new IllegalStateException("Solicitud ya procesada");
        }

        return solicitud;
    }

    private void handleRejection(LoanRequest solicitud, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Debe indicar un motivo de rechazo");
        }

        solicitud.setStatus("Rechazada");
        solicitud.setRejectionMotive(motivo);
        requestRepo.save(solicitud);

        logger.info("Solicitud rechazada: solicitudId={}, motivo={}", solicitud.getId(), motivo);
    }

    private void generarCuotas(ApprovedLoan prestamo) {
        double monthlyFee = prestamo.getInteresRate() / 12;
        double amount = prestamo.getAmount();
        int term = prestamo.getTerm();

        double cuotaMensual = amount * (monthlyFee * Math.pow(1 + monthlyFee, term)) /
                              (Math.pow(1 + monthlyFee, term) - 1);

        double totalInteres = (cuotaMensual * term) - amount;
        double interesMensual = totalInteres / term;
        double capitalMensual = amount / term;

        LocalDate fechaVencimiento = LocalDate.now().withDayOfMonth(1).plusMonths(1);

        for (int i = 0; i < term; i++) {
            Fee fee = new Fee();
            fee.setLoan(prestamo);
            fee.setTotalAmount(cuotaMensual);
            fee.setInterestAmount(interesMensual);
            fee.setCapitalAmount(capitalMensual);
            fee.setExpirationDate(fechaVencimiento.plusMonths(i));
            feeRepo.save(fee);
        }
    }


     public LoanDetailed obtenerDetallePrestamo(Long loanId) {
        ApprovedLoan loan = approvedLoanRepo.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con ID: " + loanId));

        Customers cliente = loan.getCustomer();
        List<Fee> cuotas = loan.getFees();

        // Calcular cuotas pagadas y pendientes
        long cuotasPagadas = cuotas.stream().filter(this::isPaid).count();
        long cuotasPendientes = cuotas.size() - cuotasPagadas;

        // Calcular próxima fecha de vencimiento
        LocalDate proximaVencimiento = cuotas.stream()
            .filter(fee -> !isPaid(fee))
            .map(Fee::getExpirationDate)
            .min(Comparator.naturalOrder())
            .orElse(null);

        String proximaFechaStr = (proximaVencimiento == null) ?
            "Todas las cuotas están pagadas" :
            proximaVencimiento.toString();

        // Cálculo de monto total a pagar (suma de totalAmount)
        BigDecimal montoTotalPagar = cuotas.stream()
            .map(f -> BigDecimal.valueOf(f.getTotalAmount()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Ganancia obtenida = total intereses
        BigDecimal gananciaObtenida = cuotas.stream()
            .map(f -> BigDecimal.valueOf(f.getInterestAmount()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);


         // Construir DTO
    return LoanDetailed.builder()
        .customerId(cliente.getId())
        .clientName(cliente.getFirstName() + " " + cliente.getLastName())
        .approvalDate(loan.getApprovalDate())
        .amountAsked(loan.getAmount())
        .totalAmount(montoTotalPagar.doubleValue())
        .revenueObtained(gananciaObtenida.doubleValue())
        .term(loan.getTerm())
        .loanType(loan.getLoanType())
        .interesRate(loan.getInteresRate())
        .paidFee((int) cuotasPagadas)
        .pendingFee((int) cuotasPendientes)
        .nextFeeExpiration(proximaVencimiento != null ? proximaVencimiento.toString() : "Todas las cuotas están pagadas")
        .build();

    }
        
    private boolean isPaid(Fee fee) {
           
            return fee.isPaid();
        }


}
