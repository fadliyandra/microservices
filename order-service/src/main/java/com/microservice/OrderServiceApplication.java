package com.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.microservice.webclient.CustomerClient;
import com.microservice.webclient.ProductClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

	public static void main(String[] args) { 
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	// @Bean
	// @LoadBalanced	
	// RestTemplate restTemplate(){
	// 	return new RestTemplate();
	// }


	@Autowired
	private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;

	@Bean
	WebClient webClientCustmers(){
		return WebClient.builder()
		.baseUrl("http://spring-customer-service")
		.filter(loadBalancedExchangeFilterFunction)
		.build();
	}


	@Bean
	CustomerClient customerClient(){
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
		.builder(WebClientAdapter.forClient(webClientCustmers()))
		.build();
		return factory.createClient(CustomerClient.class);
	}

	@Bean
	WebClient webClientProducts(){
		return WebClient.builder()
		.baseUrl("http://spring-product-service")
		.filter(loadBalancedExchangeFilterFunction)
		.build();
	}


	@Bean
	ProductClient productClient(){
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
		.builder(WebClientAdapter.forClient(webClientProducts()))
		.build();
		return factory.createClient(ProductClient.class);
	}

}
