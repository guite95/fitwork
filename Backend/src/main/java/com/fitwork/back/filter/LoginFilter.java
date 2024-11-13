package com.fitwork.back.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fitwork.back.util.JWTUtil;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	
	
}
