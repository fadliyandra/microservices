package com.customer.service;

import org.springframework.stereotype.Service;

import com.customer.entity.Customer;
import com.customer.repository.CustomerRepository;


@Service
public class CustomerService {
     private final CustomerRepository customerRepo;

    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }


    public Customer save(Customer customer){
        return customerRepo.save(customer);
    }
    public Customer findById(Long id){
        return customerRepo.findById(id).orElse(null);

    }

    public Iterable<Customer>findAll(){
        return customerRepo.findAll();
    }

    public Customer findByEmail(String email){
        return customerRepo.findByEmail(email);
    }
}
