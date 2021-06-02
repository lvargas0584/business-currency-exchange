package com.business.currency.exchange.core.security.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtProvider {
    String generateToken();

    Claims validateToken(String token);
}
