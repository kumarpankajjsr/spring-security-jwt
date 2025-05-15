package com.example.springsecurity.Spring_Security_JWT.config;

import com.example.springsecurity.Spring_Security_JWT.filter.JWTAuthenticationFilter;
import com.example.springsecurity.Spring_Security_JWT.service.UserAuthDetailsService;
import com.example.springsecurity.Spring_Security_JWT.utils.JWTUtils;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JWTUtils jwtUtils;
  private final UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(JWTUtils jwtUtils, UserAuthDetailsService userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }


  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(),jwtUtils);
    http.authorizeHttpRequests(
        auth -> auth.requestMatchers("/auth/register", "/h2-console/**").permitAll().anyRequest()
            .authenticated()).sessionManagement(session -> session.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS)).csrf(AbstractHttpConfigurer::disable).addFilterBefore(jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(){
    return new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(){
    DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
    dao.setUserDetailsService(userDetailsService);
    dao.setPasswordEncoder(passwordEncoder());
    return dao;
  }
}
