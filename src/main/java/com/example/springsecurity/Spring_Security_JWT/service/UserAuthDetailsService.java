package com.example.springsecurity.Spring_Security_JWT.service;

import com.example.springsecurity.Spring_Security_JWT.entity.UserAuthDetails;
import com.example.springsecurity.Spring_Security_JWT.repository.UserAuthDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthDetailsService implements UserDetailsService {

  @Autowired
  private UserAuthDetailsRepository userAuthDetailsRepository;

  public UserAuthDetails saveUser(UserAuthDetails userDetails){
    return userAuthDetailsRepository.save(userDetails);
  }

  public UserAuthDetails findUserById(Long id){
    return userAuthDetailsRepository.findById(id).orElseThrow( () -> new UsernameNotFoundException("User not Fount"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userAuthDetailsRepository.findByUsername(username);
  }
}
