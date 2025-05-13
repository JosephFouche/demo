package com.example.demo.repository;

import java.util.Optional;

import javax.swing.Spring;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.InterestRate;

public interface InterestTermRepository extends JpaRepository<InterestRate, Long>{
    
    Optional<InterestRate> findByMonthTerm(int monthTerm); 

//     Este es un método personalizado:

//     Spring lo interpreta automáticamente como una consulta SQL equivalente a:

// SELECT * FROM plazos_interes WHERE plazo_meses = ?


}
