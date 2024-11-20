package com.fitwork.back.filter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fitwork.back.member.model.dto.CustomUserDetails;
import com.fitwork.back.member.model.dto.Member;
import com.fitwork.back.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class JWTFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
	
	private final JWTUtil jwtUtil;
	public JWTFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorization = request.getHeader("Authorization");
		
		// 헤더 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			logger.info("Token is null or invalid.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Missing or malformed Authorization header.");
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println("헤더통과");
		
		String token = authorization.split(" ")[1];
		
		// 토큰 소멸시간 검증
		if (jwtUtil.isExpired(token)) {
			logger.info("Token has expired.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token expired.");
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println("소멸시간 통과");
		
		String id = jwtUtil.getId(token);
		String role = jwtUtil.getRole(token);
		
		Member member = new Member();
		member.setId(id);
		member.setPassword("temppassword");
		member.setMemberRole(role);
		
		UserDetails userDetails = new CustomUserDetails(member);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
		
	}
}