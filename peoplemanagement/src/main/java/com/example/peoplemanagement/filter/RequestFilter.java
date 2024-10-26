package com.example.peoplemanagement.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.peoplemanagement.exception.InvalidTokenException;
import com.example.peoplemanagement.service.CustomUserDetailServiceImpl;
import com.example.peoplemanagement.utils.GenerateTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
@Order
public class RequestFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);

	@Autowired
	private CustomUserDetailServiceImpl customUserDetailServiceImpl;
	@Autowired
	private GenerateTokenUtil generateTokenUtil;

	public static String passingKey = "userId";
	private static String passingUserId;

	private static List<String> skipFilterUrl = Arrays.asList("/v3/api-docs/**", "/configuration/ui",
			"/swagger-resources/**", "/configuration/security", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**",
			"/swagger-ui/**", "/people/signin", "/");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cross-Origin-Resource-Policy", "cross-site");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Disposition,Access-Control-Allow-Headers,Origin,Accept,X-Requested-With,Content-Type,"
						+ "Access-Control-Request-Method,Access-Control-Request-Headers");
		
		try {
			String headerToken = request.getHeader("Authorization");
			String username = null;
			String jwtToken = null;
			String userId = null;
			if (headerToken != null && headerToken.startsWith("Bearer")) {
				jwtToken = headerToken.substring(7, headerToken.length());
				username = generateTokenUtil.getUserNamefromToken(jwtToken);
				userId = generateTokenUtil.getUserIdfromToken(jwtToken);
				username = generateTokenUtil.getEmailfromToken(jwtToken);
				if (userId != null && !userId.equals("0") && username != null && StringUtils.hasText(jwtToken)) {
					UserDetails userDetails = this.customUserDetailServiceImpl.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					request.setAttribute(passingKey, passingUserId);
				}
			} else {
				throw new InvalidTokenException("Invalid token format");
			}
		} catch (ExpiredJwtException e) {
			request.setAttribute("exception", e);
			log.error(e.getLocalizedMessage());
		} catch (BadCredentialsException e) {
			request.setAttribute("exception", e);
			log.error(e.getLocalizedMessage());
		} catch (NullPointerException e) {
			request.setAttribute("exception", e);
			log.error(e.getLocalizedMessage());
		} catch (InvalidTokenException e) {
			request.setAttribute("exception", e);
			log.error("InvalidTokenException caught: " + e.getMessage(), e);
		}
		chain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return skipFilterUrl.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
	}
}