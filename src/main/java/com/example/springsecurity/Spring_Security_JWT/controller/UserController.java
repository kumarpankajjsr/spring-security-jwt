package com.example.springsecurity.Spring_Security_JWT.controller;

import com.example.springsecurity.Spring_Security_JWT.entity.UserAuthDetails;
import com.example.springsecurity.Spring_Security_JWT.service.UserAuthDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserAuthDetailsService userAuthDetailsService;
  @GetMapping("/get-user/{id}")
  public UserAuthDetails getUserById(@PathVariable(name = "id") Long id){
    return userAuthDetailsService.findUserById(id);
  }
}
