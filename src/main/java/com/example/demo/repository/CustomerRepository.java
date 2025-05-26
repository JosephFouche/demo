package com.example.demo.repository;
import com.example.demo.entity.Customers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers, Long>{
   
    Optional<Customers> findByUsername(String username);
    Optional<Customers> findByEmail(String email);

}
