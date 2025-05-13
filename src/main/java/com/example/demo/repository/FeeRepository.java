package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    // También podés agregar métodos personalizados, por ejemplo: findByPrestamoId(...)
}
