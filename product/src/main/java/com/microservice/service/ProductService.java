package com.microservice.service;

import org.springframework.stereotype.Service;

import com.microservice.product.Product;
import com.microservice.repository.ProductRepository;

@Service
public class ProductService {

    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Iterable<Product> findAll(){
        return productRepository.findAll();
    }


    
}
