package com.microservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.entity.UserInfo;
import com.microservice.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService{
 
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // public UserInfoService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
    //     this.userInfoRepository = userInfoRepository;
    //     this.passwordEncoder = passwordEncoder;
    // }


    public String addUser(UserInfo userInfo){

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "user info berhasi di tambahkan";

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
       Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
       return userInfo.map(UserInfoDetails::new).
       orElseThrow(() -> new UsernameNotFoundException("User not found"));
        

    }

}
