package com.example.demo.service.simulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.SimulationRequest;
import com.example.demo.dto.SimulationResponse;
import com.example.demo.entity.InterestRate;

import com.example.demo.repository.InterestTermRepository;

@Service
public class SimulationService {
    
    @Autowired
    private InterestTermRepository termRepo;

    public SimulationResponse simulate(SimulationRequest request){
        InterestRate termInterest= termRepo.findByMonthTerm(request.getMonthTerm())
                .orElseThrow(() -> new IllegalArgumentException("Plazo no válido"));

        double annualRate = termInterest.getInterestRate();
        double monthlyRate = annualRate / 12;
        double monto = request.getAmount();
        int plazo = request.getMonthTerm();

        // Fórmula del sistema francés
        double cuotaMensual = monto * (monthlyRate * Math.pow(1 + monthlyRate, plazo)) /
                              (Math.pow(1 + monthlyRate, plazo) - 1);

        double totalPagar = cuotaMensual * plazo;

        return new SimulationResponse(cuotaMensual, totalPagar);
    }

}
