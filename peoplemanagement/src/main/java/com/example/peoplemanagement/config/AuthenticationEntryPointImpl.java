package com.example.peoplemanagement.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException {
					
		String exception = (String) request.getAttribute("exception");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		if (exception!=null){
		        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,exception);
		    }else{
		        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Login details");
		    }
	}
}