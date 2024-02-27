package com.microservice.webclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.microservice.dto.Customer;

@HttpExchange
public interface CustomerClient {

    @GetExchange("/api/customers/{id}")
    public Customer findById(@PathVariable("id")Long id);
}
