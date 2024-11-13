package com.fitwork.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fitwork.back.util.JWTUtil;

public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf((auth) -> auth.disable());
		
		httpSecurity.formLogin((auth) -> auth.disable());
		
		httpSecurity.httpBasic((auth) -> auth.disable());
		
		httpSecurity
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/login", "/join").permitAll()
						.requestMatchers("/admin").hasRole("ADMIN")
						.anyRequest().authenticated());
		
//		httpSecurity.addFilter();
		
		return null;
		
	}
	
}
