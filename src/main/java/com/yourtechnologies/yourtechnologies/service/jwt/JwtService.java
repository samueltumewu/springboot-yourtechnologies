package com.yourtechnologies.yourtechnologies.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JwtService extends BaseJwtService implements IJwtService {
    /*
        GENERATE TOKEN
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    @Override
    public String generateToken(HashMap<String, Object> mapExtraClaims, UserDetails userDetails) {
        return buildToken(mapExtraClaims, userDetails, getExpirationTime());
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
    @Override
    public Long getExpirationTime() {
        return jwtExpirationDurationMs;
    }
}
