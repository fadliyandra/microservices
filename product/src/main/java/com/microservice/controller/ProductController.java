package com.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.product.Product;
import com.microservice.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product save(@RequestBody Product product){
        return productService.save(product); 
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable ("id") Long id){
        return productService.findById(id);
    }

    @GetMapping
    public Iterable <Product> findAll(){
        return productService.findAll();
    }


    
}
