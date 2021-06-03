package com.business.currency.exchange.security.api;

import com.business.currency.exchange.core.dto.security.User;
import com.business.currency.exchange.security.filter.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/auth")
    public User login(@RequestParam("user") String username) {

        String token = jwtProvider.getJWTToken(username);
        User user = new User();
        user.setUser(username);
        user.setToken(token);
        return user;

    }


}
