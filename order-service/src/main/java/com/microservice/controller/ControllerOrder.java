package com.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.dto.OrderResponse;
import com.microservice.entity.Order;
import com.microservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/api/orders")
public class ControllerOrder {

  private final OrderService orderService;

    public ControllerOrder(OrderService orderService) {
    this.orderService = orderService;
    }

    
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable("id") Long id){
        return orderService.findById(id);
    }

    @GetMapping("/order-number/{number}")
    public OrderResponse findByOrderNumber(@PathVariable("number") String number){
        return orderService.findByOrderNumber(number);
    }

    @PostMapping
    public Order save(@RequestBody Order order){
        return orderService.save(order);
    }


    
}
