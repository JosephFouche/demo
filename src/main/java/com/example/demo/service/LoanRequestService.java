package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.InterestRate;
import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.InterestTermRepository;
import com.example.demo.repository.LoanRequestRepository;

@Service
public class LoanRequestService {

    @Autowired
    private InterestTermRepository interestRepo;

    @Autowired
    private LoanRequestRepository requestRepo;

    public LoanRequest createLoanRequest(LoanRequestDTO dto) {
        InterestRate term = interestRepo.findByMonthTerm(dto.getTerm())
            .orElseThrow(() -> new IllegalArgumentException("Plazo no válido"));

        LoanRequest request = new LoanRequest();
        request.setLoanType(dto.getLoanType());
        request.setAmount(dto.getAmount());
        request.setTerm(dto.getTerm());
        request.setStatus("Pendiente de Aprobación");

        return requestRepo.save(request);
    }
}
