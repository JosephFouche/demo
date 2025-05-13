package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}