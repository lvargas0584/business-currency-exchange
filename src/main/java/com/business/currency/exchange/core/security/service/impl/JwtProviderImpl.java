package com.business.currency.exchange.core.security.service.impl;

import com.business.currency.exchange.core.security.service.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtProviderImpl implements JwtProvider {


    @Value("${jwt.secret.key}")
    String secretKey;

    /* @Value("${issuer}")
     String issuer;

     @Value("${audience}")
     String audience;
 */
    @Value("${jwt.expiration.time}")
    Long expirationTime;

    @Override
    public String generateToken() {
        Claims _claims = Jwts.claims();

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String token = Jwts.builder()
                .setClaims(_claims).signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date())
                //.setIssuer(jwtConfiguration.issuer())
                //.setAudience(jwtConfiguration.audience())
                .compact();

        return token;
    }

    @Override
    public Claims validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(resolve(token)).getBody();

            HashMap<String, String> map = new HashMap();

            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                map.put(entry.getKey(), entry.getValue().toString());
            }

            return claims;
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String resolve(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }
        return null;
    }
}