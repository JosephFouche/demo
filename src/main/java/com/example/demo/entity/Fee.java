package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Fee {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private ApprovedLoan loan;

    private double totalAmount;
    private double capitalAmount;
    private double interestAmount;
    private LocalDate expirationDate;


    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ApprovedLoan getLoan() {
        return loan;
    }
    public void setLoan(ApprovedLoan loan) {
        this.loan = loan;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public double getCapitalAmount() {
        return capitalAmount;
    }
    public void setCapitalAmount(double capitalAmount) {
        this.capitalAmount = capitalAmount;
    }
    public double getInterestAmount() {
        return interestAmount;
    }
    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    
    
    
}
