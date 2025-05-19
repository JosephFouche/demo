package com.example.demo.repository;
import com.example.demo.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers, Long>{
    

}
