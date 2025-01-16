package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BankResponse;
import com.example.demo.dto.CreditDebitRequest;
import com.example.demo.dto.EnquiryRequest;
import com.example.demo.dto.TransferRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.service.UserServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class UserController {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Operation(summary = "Create New User Account", description = "Creating a new user and assigning an account ID")
    @ApiResponse(responseCode = "201", description = "http status 201 created")
    // Endpoint para crear una cuenta
    @PostMapping("/api/user/createAccount")
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userServiceImplementation.createAccount(userRequest);
    }

    // Endpoint para consultar balance, usando parámetros de consulta
    @Operation(summary = "Balance Enquiry", description = "Given an user account, check how much the user has")
    @ApiResponse(responseCode = "200", description = "http status 200 created")
    @GetMapping("/balanceEnquiry/{accountNumber}")
    public ResponseEntity<BankResponse> balanceEnquiry(@PathVariable String accountNumber) {
        EnquiryRequest enquiryRequest = new EnquiryRequest();
        enquiryRequest.setAccountNumber(accountNumber);

        BankResponse response = userServiceImplementation.balanceEnquiry(enquiryRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nameEnquiry/{accountNumber}")
    public ResponseEntity<String> nameEnquiry(@PathVariable String accountNumber) {
        EnquiryRequest enquiryRequest = new EnquiryRequest();
        enquiryRequest.setAccountNumber(accountNumber);

        String response = userServiceImplementation.nameEnquiry(enquiryRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/user/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
        return userServiceImplementation.debitAccount(request);
    }

    @PostMapping("/api/user/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request) {
        return userServiceImplementation.transfer(request);
    }

}
