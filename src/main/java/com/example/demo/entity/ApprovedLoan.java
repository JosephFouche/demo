package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
@Entity
public class ApprovedLoan {
     @Id @GeneratedValue
    private Long id;
//1 cliente muchos pedidos aprovados
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customers customer;
//un prestamo, muchas cuotas
//"loan" es el nombre del atributo 
//en la clase Fee que contiene la relación @ManyToOne hacia esta entidad.
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Fee> fees;


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
