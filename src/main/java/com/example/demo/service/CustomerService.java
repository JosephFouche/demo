package com.example.demo.service;



import com.example.demo.entity.Customers;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customersRepository;

    @Autowired
    public CustomerService(CustomerRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

      // Buscar cliente por ID
    public Customers getCustomerById(Long id) {
        return customersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
    }

    //LIstar todos los clientes
    public List<Customers> getAllCustomers(){
        return customersRepository.findAll();
    }
    
    //crear nuevo cliente
    public Customers createCustomer(Customers customer){
         
        return customersRepository.save(customer);
    }

    //eliminar cliente
    public void deleteCustomer(Long id){
        if(!customersRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente no existe");
        }
        customersRepository.deleteById(id);
    }
}
