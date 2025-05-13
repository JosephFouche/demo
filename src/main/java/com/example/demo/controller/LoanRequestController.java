package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoanApprovalRequest;
import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;

@RestController
@RequestMapping("/api/loan-request")
public class LoanRequestController {

    @Autowired
    private LoanRequestService requestService;

    @PostMapping
    public LoanRequest createRequest(@RequestBody LoanRequestDTO dto) {
        return requestService.createLoanRequest(dto);
    }
    @PostMapping("/approve-or-reject")
    public ResponseEntity<String> approveOrReject(@RequestBody LoanApprovalRequest request) {
        requestService.approveOrReject(request);
        return ResponseEntity.ok("Solicitud procesada");
    }
}