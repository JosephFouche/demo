package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.ApprovedLoan;

public interface ApprovedLoanRepository extends JpaRepository<ApprovedLoan, Long> {
    // Podés agregar métodos personalizados si necesitás buscar préstamos por cliente, fecha, etc.
}
