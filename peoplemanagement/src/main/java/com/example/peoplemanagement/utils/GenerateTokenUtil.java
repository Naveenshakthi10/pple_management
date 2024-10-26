package com.example.peoplemanagement.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.example.peoplemanagement.Form.JWTTokenInfo;
import com.example.peoplemanagement.model.UserInformation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("serial")
@Slf4j
@Component
public class GenerateTokenUtil implements Serializable {

	@SuppressWarnings("unused")
	private static final long SerialVersionUID = 1L;

	@Value("${jwt.expiration}")
	private long TOKENEXIPRATIONTIME;

	@Value("${jwt.expiration}")
	private long TOKENREFRESHTIME;

	@Value("${jwt.secret}")
	private String SIGNINKEY;

	public String generateJwtToken(UserInformation userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userName", userDetails.getName());
		claims.put("email", userDetails.getEmail());
		claims.put("role", userDetails.getRole());
		claims.put("userId", userDetails.getUserId());
		return doGenerateToken(claims);
	}

	@SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims) {
		String token = null;
		try {
			token = Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + TOKENEXIPRATIONTIME))
					.signWith(SignatureAlgorithm.HS256, SIGNINKEY.getBytes()).compact();
		} catch (Exception e) {
		}
		return token;
	}

	@SuppressWarnings("deprecation")
	public String doGenerateRefreshToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKENREFRESHTIME))
				.signWith(SignatureAlgorithm.HS256, SIGNINKEY.getBytes()).compact();

	}

	@SuppressWarnings("deprecation")
	public boolean validToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SIGNINKEY.getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		} catch (ExpiredJwtException e) {
			throw e;
		}

	}

	@SuppressWarnings("deprecation")
	public String getUserNamefromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNINKEY.getBytes()).parseClaimsJws(token).getBody();
		return String.valueOf(claims.get("userName"));
	}

	@SuppressWarnings("deprecation")
	public String getEmailfromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNINKEY.getBytes()).parseClaimsJws(token).getBody();
		return String.valueOf(claims.get("email"));
	}

	@SuppressWarnings("deprecation")
	public String getRolefromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNINKEY.getBytes()).parseClaimsJws(token).getBody();
		return String.valueOf(claims.get("role"));
	}

	@SuppressWarnings("deprecation")
	public String getUserIdfromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNINKEY.getBytes()).parseClaimsJws(token).getBody();
		return String.valueOf(claims.get("userId"));
	}

	public JWTTokenInfo getJWTTokenInfo(HttpServletRequest request) {
		String token = resolveToken(request);
		if (StringUtils.isNotBlank(token)) {
			return getTokenInfo(token);
		}
		return null;

	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Cacheable(cacheNames = "JWTTokenInfo", key = "#token")
	private JWTTokenInfo getTokenInfo(String token) {
		if (StringUtils.isNotBlank(token)) {
			try {
				Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNINKEY.getBytes()).parseClaimsJws(token);
				if (ObjectUtils.isNotEmpty(claims) && ObjectUtils.isNotEmpty(claims.getBody())) {
					String userName = getUserNamefromToken(token);
					String email = getUserNamefromToken(token);
					String role = getUserNamefromToken(token);
					String userId = getUserIdfromToken(token);
					String issuer = claims.getBody().getIssuer();
					Date expiry = claims.getBody().getExpiration();
					JWTTokenInfo jwtTokenInfo = new JWTTokenInfo();
					jwtTokenInfo.setEmail(email);
					jwtTokenInfo.setExpiryDate(expiry);
					jwtTokenInfo.setIssuer(issuer);
					jwtTokenInfo.setRole(role);
					jwtTokenInfo.setUserId(userId);
					jwtTokenInfo.setUserName(userName);
					return jwtTokenInfo;
				}
			} catch (Exception e) {
				throw new JwtException("Token is invalid format");
			}
		} else {
			throw new JwtException("Token should not be null");
		}
		return new JWTTokenInfo();
	}

}
