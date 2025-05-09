package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Genera un constructor sin argumentos.

@AllArgsConstructor // Genera un constructor con argumentos para todos los campos de la clase.

@Builder // Permite construir instancias de la clase utilizando el patrón Builder (útil
         // para crear objetos complejos de forma fluida).

@Entity // Marca la clase como una entidad JPA,
// lo que indica que está mapeada a una tabla en la base de datos.

@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
    public String getAccountNumber() {//05/05/25,
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {//05/05/25,
        this.accountNumber = accountNumber;
    }

    public String getTransactionType(){//05/05/25,
        return transactionType;
    }
    public BigDecimal getAmount(){//05/05/25,
        return amount;
    }
    public String getStatus(){//05/05/25,
        return status;
    }
    @CreationTimestamp
    private LocalDateTime createdAt;//lcaldate por localdatetime 05/05/25, puse getter y setter
 public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @UpdateTimestamp
    private LocalDate modifiedAt;
}
