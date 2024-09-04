package com.yourtechnologies.yourtechnologies.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class BaseJwtService {
    @Value("${jwt.secret-key}")
    protected String jwtSecretKey;
    @Value("${jwt.expiration-duration-ms}")
    protected Long jwtExpirationDurationMs;

    public String extractUsername(String token) {
        return extraClaim(token, Claims::getSubject);
    }
    protected <T> T extraClaim(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(extractAllClaims(token));
    }
    protected Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    protected Date extractExpiration(String token) {
        return extraClaim(token, Claims::getExpiration);
    }
    protected String buildToken(HashMap<String, Object> mapExtraClaims, UserDetails userDetails, Long jwtExpirationDurationMs) {
        return Jwts.builder()
                .claims(mapExtraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationDurationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }
    protected boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    protected SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
