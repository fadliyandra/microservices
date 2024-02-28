package com.microservice.controller;

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

    public UserController(UserInfoService userInfoService, JwtService jwtService) {
        this.userInfoService = userInfoService;
        this.jwtService = new JwtService();
    }

    @PostMapping("/register")
    public String addUser(@RequestBody UserInfo userInfo){
        userInfoService.addUser(userInfo);
        return "usser berhasil di tambahkan";
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest){
        return jwtService.generateToken(authRequest.getName());

    }
    

}
