package com.example.springsecurity.Spring_Security_JWT.controller;

import com.example.springsecurity.Spring_Security_JWT.entity.UserAuthDetails;
import com.example.springsecurity.Spring_Security_JWT.service.UserAuthDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class UserAuthController {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  UserAuthDetailsService userAuthDetailsService;
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody UserAuthDetails userDetails){
    userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
    userAuthDetailsService.saveUser(userDetails);

    return new ResponseEntity<>("User Registered Successfully",HttpStatus.OK);
  }
}
