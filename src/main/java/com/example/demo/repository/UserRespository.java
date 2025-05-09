package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.*;

//La interfaz UserRespository extiende JpaRepository, 
//lo que significa que hereda una serie de métodos
//para realizar operaciones CRUD (crear, leer, actualizar, eliminar) 
//y consultas en la base de datos.

/* 
#Realiza la operación de acceso a datos (como obtener registros, guardarlos o actualizarlos).*/
//User como la entidad que este repositorio va a gestionar
//Long= Especifica el tipo de la clave primaria de la entidad User (el campo id en este caso).
public interface UserRespository extends JpaRepository<User, Long> {

    // check if a recorded database exists, email for example
    Boolean existsByEmail(String email);

    Boolean existsByAccountNumber(String accountNumber);

    User findByAccountNumber(String accountNumber);

}
