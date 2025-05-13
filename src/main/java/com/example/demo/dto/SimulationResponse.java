package com.example.demo.dto;

public class SimulationResponse {
    
    private double monthlyPayment;
    private double totalAmount;

    public SimulationResponse(double monthlyPayment, double totalAmount){
        this.monthlyPayment = monthlyPayment;
        this.totalAmount = totalAmount;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
