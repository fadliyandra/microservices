package com.microservice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.dto.AuthRequest;
import com.microservice.entity.UserInfo;
import com.microservice.service.JwtService;
import com.microservice.service.UserInfoService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserInfoService userInfoService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;



    public UserController(UserInfoService userInfoService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userInfoService = userInfoService;
        this.jwtService = new JwtService();
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String addUser(@RequestBody UserInfo userInfo){
        userInfoService.addUser(userInfo);
        return "usser berhasil di tambahkan";
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getName()));

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getName());
                
            }else{
                throw  new RuntimeException("Invalid username password");
            }


     //   return jwtService.generateToken(authRequest.getName());

    }
    

}
