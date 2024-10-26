package com.example.peoplemanagement.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.peoplemanagement.model.UserInformation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class GenerateTokenServiceImpl implements GenerateTokenService {

	    @Autowired
	    private JwtBuilder jwtBuilder;

	    @Value("${jwt.secret}")
	    private String secret;

	    @Value("${jwt.issuer}")
	    private String issuer;

	    @Value("${jwt.audience}")
	    private String audience;

	    @Value("${jwt.expiration}")
	    private long expiration;

	    @SuppressWarnings("deprecation")
		public String generateJwtToken(UserInformation userDetails) {
	        Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + expiration);

	        Claims claims = Jwts.claims().setSubject(userDetails.getName());
	        claims.put("email", userDetails.getEmail());
	        claims.put("role", userDetails.getRole());

	        return jwtBuilder
	                .setClaims(claims)
	                .setIssuedAt(now)
	                .setExpiration(expiryDate)
	                .setIssuer(issuer)
	                .setAudience(audience)
	                .signWith(SignatureAlgorithm.HS256, secret)
	                .compact();
	    }
	    
	    public boolean validateToken(String token) {
	        try {
	            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	            return true;
	        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
	            // Handle invalid/expired token exceptions
	            return false;
	        }
	    }

	    public UserInformation getUserFromToken(String token) {
	        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	        // Extract user information from claims and return UserDetails object
	        // You may need to adjust this part based on how you structure your UserDetails
	        return new UserInformation();
	    }

}
