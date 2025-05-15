package com.example.springsecurity.Spring_Security_JWT.filter;

import com.example.springsecurity.Spring_Security_JWT.entity.LoginRequest;
import com.example.springsecurity.Spring_Security_JWT.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;
  private final JWTUtils jwtUtils;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (!request.getServletPath().equalsIgnoreCase("/generate-token")) {
      filterChain.doFilter(request, response);
      return;
    }

    ObjectMapper objectMapper = new ObjectMapper();
    LoginRequest loginRequest =
        objectMapper.readValue(request.getInputStream(), LoginRequest.class);

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword());

    Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    if(authentication.isAuthenticated()){
      String token = jwtUtils.generateToken(authentication.getName(),1);
      response.setHeader("Authorization","Bearer " + token);
    }
  }
}
