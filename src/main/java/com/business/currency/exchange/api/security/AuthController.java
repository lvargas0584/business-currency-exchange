package com.business.currency.exchange.api.security;

import com.business.currency.exchange.core.dto.security.User;
import com.business.currency.exchange.core.security.service.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthController {


    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("auth")
    public User login(@RequestParam("user") String username) {
        String token = jwtProvider.generateToken();
        User user = new User();
        user.setUser(username);
        user.setToken(token);
        return user;

    }

    private String getJWTToken(String username) {
        String secretKey = "4d182608-beee-4470-96f9-0b1d2a3cffcd";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String token = Jwts
                .builder()
                .setId("exchangeJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        return "Bearer " + token;
    }

    public String generateToken() {
        String secretKey = "4d182608-beee-4470-96f9-0b1d2a3cffcd";
        //Claims _claims = Jwts.claims();

        /*for(Map.Entry<String, String> entry :  claims.entrySet()) {
            _claims.put(entry.getKey(), entry.getValue());
        }*/

        //expirationTime = expirationTime != null ? expirationTime :  600000;
        Key key = Keys.hmacShaKeyFor(generateSafeToken().getBytes());

        String token = Jwts.builder()
                //.setClaims(_claims)
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .setIssuedAt(new Date())
                .setIssuer("http://hlw.kantoo.com/")
                .setAudience("http://hlw.kantoo.com/")
                .compact();

        return token;
    }

    private String generateSafeToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36]; // 36 bytes * 8 = 288 bits, a little bit more than
        // the 256 required bits
        random.nextBytes(bytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
