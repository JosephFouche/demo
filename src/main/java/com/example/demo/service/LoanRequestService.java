package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoanApprovalRequest;
import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.ApprovedLoan;
import com.example.demo.entity.Customers;
import com.example.demo.entity.Fee;
import com.example.demo.entity.InterestRate;
import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.InterestTermRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.ApprovedLoanRepository;
import com.example.demo.repository.FeeRepository;

@Service
public class LoanRequestService {

    @Autowired
    private InterestTermRepository interestRepo;

    @Autowired
    private LoanRequestRepository requestRepo;

      @Autowired
    private ApprovedLoanRepository approvedLoanRepo;

     @Autowired
    private FeeRepository feeRepo;
//👉 Este sirve para que el cliente registre una solicitud de préstamo.
    public LoanRequest createLoanRequest(LoanRequestDTO dto) {
        InterestRate term = interestRepo.findByMonthTerm(dto.getTerm())
            .orElseThrow(() -> new IllegalArgumentException("Plazo no válido"));

        LoanRequest request = new LoanRequest();
        request.setLoanType(dto.getLoanType());
        request.setAmount(dto.getAmount());
        request.setTerm(dto.getTerm());
        request.setCustomer(Cliente);
        request.setStatus("Pendiente de Aprobación");

        return requestRepo.save(request);
    }
    //obtener tasa de interes
    private double obtainInterestRate(int term){
        return interestRepo.findByMonthTerm(term)
        .map(InterestRate::getInterestRate)
            .orElseThrow(() -> new IllegalArgumentException("Plazo no válido o sin tasa configurada"));
    }

    public void approveOrReject(LoanApprovalRequest request) {
        LoanRequest solicitud = requestRepo.findById(request.getIdRequest())
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        if (!solicitud.getStatus().equals("Pendiente de Aprobación")) {
            throw new IllegalStateException("Solicitud ya procesada");
        }

        if (!request.isApprove()) {
            if (request.getRejectionMotive() == null || request.getRejectionMotive().isBlank()) {
                throw new IllegalArgumentException("Debe indicar un motivo de rechazo");
            }
            solicitud.setStatus("Rechazada");
            solicitud.setRejectionMotive(request.getRejectionMotive());
            requestRepo.save(solicitud);
            return;
        }

        // Aprobación
        solicitud.setStatus("Aprobada");
        requestRepo.save(solicitud);

        double tasa = obtainInterestRate(solicitud.getTerm()); // Tu método propio

        ApprovedLoan prestamo = new ApprovedLoan();
        prestamo.setCustomer(solicitud.getCustomer());
        prestamo.setApprovalDate(LocalDate.now());
        prestamo.setAmount(solicitud.getAmount());
        prestamo.setTerm(solicitud.getTerm());
        prestamo.setLoanType(solicitud.getLoanType());
        prestamo.setInteresRate(tasa);
        approvedLoanRepo.save(prestamo);

        generarCuotas(prestamo);
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


}
