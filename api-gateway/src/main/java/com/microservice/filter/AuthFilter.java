package com.microservice.filter;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microservice.utils.JwtUtils;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private RestTemplate restTemplate;

    public AuthFilter(){
        super(Config.class);
    }

    public static class Config {
    
    }

    @Override
    public GatewayFilter apply(Config config) {
        // TODO Auto-generated method stub
        return (exchange, chain)-> {
            //do something

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                    
                }
                List<String> authHeaderValues = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
                if (authHeaderValues !=null && !authHeaderValues.isEmpty()) {
                    String token = authHeaderValues.get(0);
                    if(token !=null && token.startsWith("Bearer ")){
                        token = token.substring(7);
                    }
                    //validasi token
                    try {
                        
                        // restTemplate.getForObject("http://localhost:8099/api/auth/validateToken?token"+ token,
                        //  String.class);
                        jwtUtils.validateToken(token);

                    } catch (Exception ex) {

                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                        // TODO: handle exception
                    }
                    
                }else{
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                }
                
            }
            return chain.filter(exchange);
        };
    }

    
    
}
