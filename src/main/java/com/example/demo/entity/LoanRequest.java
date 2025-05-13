package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_requests")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanType; // Ej: Personal, Hipotecario, etc.

    private int term; // En meses

    private double amount;

    @ManyToOne(optional = false)
@JoinColumn(name = "customer_id", nullable = false)
    private Customers customer;

    private String status; // Ej: "Pendiente de Aprobación"

    private String rejectionMotive; // solo si es Rechazada

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectionMotive() {
        return rejectionMotive;
    }

    public void setRejectionMotive(String rejectionMotive) {
        this.rejectionMotive = rejectionMotive;
    }
    
}