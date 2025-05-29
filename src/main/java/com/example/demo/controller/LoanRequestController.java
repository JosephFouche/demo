package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoanApprovalRequest;
import com.example.demo.dto.LoanDetailed;
import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;

import lombok.RequiredArgsConstructor;

import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/api/loan-request")
@RequiredArgsConstructor
public class LoanRequestController {

    private final LoanRequestService requestService;
    private final CustomerService customerService;

   

    @PostMapping
    public LoanRequest createRequest(@RequestBody LoanRequestDTO dto) {
        return requestService.createLoanRequest(dto);
    }
    @PostMapping("/approve-or-reject")
    public ResponseEntity<String> approveOrReject(@RequestBody LoanApprovalRequest request) {
        requestService.approveOrReject(request);
        return ResponseEntity.ok("Solicitud procesada");


    }
    /**
     * Endpoint protegido que devuelve los detalles de un préstamo aprobado.
     * Ejemplo de uso: GET /api/loans/{loanId}/details
     */
   
    @GetMapping("/{loanId}/details")
@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
public ResponseEntity<LoanDetailed> getLoanDetails(@PathVariable Long loanId) {
    LoanDetailed detalles = requestService.obtenerDetallePrestamo(loanId);
    return ResponseEntity.ok(detalles);
}
    
}