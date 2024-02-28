package com.microservice.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.dto.Customer;
import com.microservice.dto.OrderLineResponse;
import com.microservice.dto.OrderResponse;
import com.microservice.dto.Product;
import com.microservice.entity.Order;
import com.microservice.entity.OrderLine;
import com.microservice.repository.OrderRepository;
import com.microservice.webclient.CustomerClient;
import com.microservice.webclient.ProductClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class OrderService {

  //  private final RestTemplate restTemplate;

    private final OrderRepository orderRepository;

    private final CustomerClient customerClient;

    private final ProductClient productClient;

 

    public OrderService(OrderRepository oerderRepository,
    // RestTemplate restTemplate, 
    CustomerClient customerClient, ProductClient productClient) {
      //  this.restTemplate = restTemplate;
        this.orderRepository = oerderRepository;
        this.customerClient = customerClient;
        this.productClient = productClient;
    }


    public Order save(Order order){
            for(OrderLine orderline : order.getOrderLines()){
                orderline.setOrder(order);
            }
            return orderRepository.save(order);
    }


   // @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackFindCustomerById")
    public OrderResponse findById(Long id){
        Optional<Order> optOrder = orderRepository.findById(id);
        if (!optOrder.isPresent()) {
            return null;
        }
        Order order = optOrder.get();
        OrderResponse response = new OrderResponse(order.getId(),order.getOrderNumber(),
         order.getOrderDate(), 
         customerClient.findById(order.getCustomerId()), new ArrayList<OrderLineResponse>());

         for(OrderLine orderLine : order.getOrderLines()){
            Product product = productClient.findById(orderLine.getProductId());
            response.getOrderLines().add(new OrderLineResponse(orderLine.getId(), 
            product, orderLine.getQuantity(),orderLine.getPrice()));
         }

        return response;
    }

    private OrderResponse fallbackFindCustomerById(Long id, Throwable throwable){
        return new OrderResponse();
    }




    public OrderResponse findByOrderNumber(String orderNumber){
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) {
            return null;
        }
  
        OrderResponse response = new OrderResponse(order.getId(),order.getOrderNumber(),
         order.getOrderDate(), 
         customerClient.findById(order.getCustomerId()), new ArrayList<OrderLineResponse>());

         for(OrderLine orderLine : order.getOrderLines()){
            Product product = productClient.findById(orderLine.getProductId());
            response.getOrderLines().add(new OrderLineResponse(orderLine.getId(), 
            product, orderLine.getQuantity(),orderLine.getPrice()));
         }

        return response;
    }



//     public Customer findCustomerById(Long id){
//          return restTemplate.getForObject("http://SPRING-CUSTOMER-SERVICE/api/customers/" + id, Customer.class);

//     }

//     public Product findProductById(Long id){
//         return restTemplate.getForObject("http://SPRING-PRODUCT-SERVICE/api/products/" + id, Product.class);

//    }
}
