package com.yourtechnologies.yourtechnologies.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;

public interface IJwtService {
    String generateToken(UserDetails userDetails);
    String generateToken(HashMap<String, Object> mapExtraClaims, UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    Long getExpirationTime();
}
