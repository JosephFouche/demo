package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Table(name = "interest_rates") // Nombre de la tabla en la base de datos

public class InterestRate {

    @Id@GeneratedValue
    private Long Id;
    private int monthTerm;
    private double interestRate;
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public int getMonthTerm() {
        return monthTerm;
    }
    public void setMonthTerm(int monthTerm) {
        this.monthTerm = monthTerm;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    
}
