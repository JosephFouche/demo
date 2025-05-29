package com.example.demo.service;

import com.example.demo.entity.Customers;
import com.example.demo.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailedService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDetailedService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Customers usuario = customerRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

    return new org.springframework.security.core.userdetails.User(
        usuario.getEmail(),
        usuario.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_USER"))  // ✅ asigna el rol
    );
}

    }
