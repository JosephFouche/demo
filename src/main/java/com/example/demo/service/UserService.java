package com.example.demo.service;

import com.example.demo.dto.BankResponse;
import com.example.demo.dto.CreditDebitRequest;
import com.example.demo.dto.EnquiryRequest;
import com.example.demo.dto.TransferRequest;
import com.example.demo.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);// bankresponse is the return type
    // user request como parametro

    // balance enquiry
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);

    BankResponse transfer(TransferRequest request);
}
