package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class ApprovedLoan {
     @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Customers customer;

    private LocalDate approvalDate;
    private double amount;
    private int term;
    private String loanType;
    private double interesRate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Customers getCustomer() {
        return customer;
    }
    public void setCustomer(Customers customer) {
        this.customer = customer;
    }
    public LocalDate getApprovalDate() {
        return approvalDate;
    }
    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public int getTerm() {
        return term;
    }
    public void setTerm(int term) {
        this.term = term;
    }
    public String getLoanType() {
        return loanType;
    }
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    public double getInteresRate() {
        return interesRate;
    }
    public void setInteresRate(double interesRate) {
        this.interesRate = interesRate;
    }

    
}
