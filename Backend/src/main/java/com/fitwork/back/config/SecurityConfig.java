package com.fitwork.back.config;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import com.fitwork.back.filter.JWTFilter;
import com.fitwork.back.filter.LoginFilter;
import com.fitwork.back.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
@Configuration
@EnableWebSecurity
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
						.requestMatchers(
								"/api-member/**",
								"/", "/file/**", 
								"/api-board/list", 
								"/api-class/list/**", 
								"/api-class/detail/**",
								"/api-club/list/**",
								"/api-club/detail/**",
								"/api-admin/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated());
		
		// JWT필터 추가
		httpSecurity.addFilterAfter(new JWTFilter(jwtUtil), LoginFilter.class);
		
		// Login 필터 추가
		httpSecurity
				.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		// 세션을 stateless로 설정
		httpSecurity
				.sessionManagement((session) -> session
												.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// CORS 설정
		httpSecurity
				.cors((corsCustomizer) -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
					
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration configuration = new CorsConfiguration();
						
//						configuration.setAllowedOrigins(Collections.singletonList("http://192.168.210.83:5173"));
						configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
						configuration.setAllowedMethods(Collections.singletonList("*"));
						configuration.setAllowCredentials(true);
						configuration.setAllowedHeaders(Collections.singletonList("*"));
						configuration.setMaxAge(3600L);
						configuration.setExposedHeaders(Collections.singletonList("Authorization"));
						
						return configuration;
					}
				}));
		
		return httpSecurity.build();
		
	}
	
	// AuthenticationManager bean 등록
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}