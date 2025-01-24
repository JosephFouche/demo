package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

@Table(name = "users") // Especifica que esta entidad está vinculada a la tabla llamada users en la
                       // base de datos.
public class User {

    @Id // Marca el campo id como la clave primaria de la tabla.

    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que el valor de la clave primaria (id)
    // será generado automáticamente por la base de datos mediante una estrategia de
    // auto-incremento.
    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;
    @CreationTimestamp // Automáticamente establece la fecha y hora en que se creó el registro.
    private LocalDateTime createdAt;
    @UpdateTimestamp // Automáticamente actualiza la fecha y hora cada vez que el registro se
                     // modifica.
    private LocalDateTime modifiedAt;

    // Los datos de una fila en la tabla users se mapean a un objeto de la clase
    // User.
    // Al guardar un objeto User, se inserta/actualiza una fila en la tabla users.
}
