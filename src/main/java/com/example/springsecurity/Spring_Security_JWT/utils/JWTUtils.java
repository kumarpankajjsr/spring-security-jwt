package com.example.springsecurity.Spring_Security_JWT.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

  private static final String SECRET_KEY = "this-is-my-secret-key-min-32bytes";
  private static final Key key =  Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

  public String generateToken(String name,long expiryMinute){
     return Jwts.builder().setSubject(name).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiryMinute * 60 * 1000)).signWith(key,
         SignatureAlgorithm.HS256).compact();
  }
}
