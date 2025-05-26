package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailed {
    
    private long customerId;// customers.id
    private String clientName;// customers.firstName + " " + customers.lastName
    private LocalDate approvalDate;// approvedLoan.approvalDate
    private double amountAsked;// approvedLoan.amount
    private double totalAmount;// suma de todas las fee.totalAmount
    private double revenueObtained;// montoTotalPagar - montoSolicitado
    private int term;// approvedLoan.term
    private String loanType;// approvedLoan.loanType
    private double interesRate;// approvedLoan.interesRate
    private int paidFee;// count de cuotas pagadas (si tenés un campo de estado)
    private int pendingFee;// totalCuotas - cuotasPagadas
    private String nextFeeExpiration;// próxima fee.expirationDate de cuota pendiente

    
}
