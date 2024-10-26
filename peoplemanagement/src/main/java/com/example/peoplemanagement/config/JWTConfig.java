package com.example.peoplemanagement.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Configuration
public class JWTConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;
    
    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder();
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public SignatureAlgorithm getAlgorithm() {
        return SignatureAlgorithm.HS256;
    }

    public int getExpiration() {
        return expiration;
    }
}
