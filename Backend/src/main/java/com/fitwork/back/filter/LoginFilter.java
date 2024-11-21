package com.fitwork.back.filter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwork.back.member.model.dto.CustomUserDetails;
import com.fitwork.back.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.setFilterProcessesUrl("/api-member/login");
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    // request의 InputStream에서 JSON 데이터를 Map으로 변환
	    Map<String, String> credentials;
		try {
			credentials = objectMapper.readValue(request.getInputStream(), Map.class);
			
			String id = credentials.get("id");
			String password = credentials.get("password");
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id, password);
			
			return authenticationManager.authenticate(authToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// 로그인 성공 시 동작하는 메서드. JWT 발급
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		try {
			CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
			
			String id = customUserDetails.getUsername();
			
			Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
			Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
			GrantedAuthority auth = iterator.next();
			
			String role = auth.getAuthority();
			
			String token = jwtUtil.createJwt(id, role);
			response.addHeader("Authorization", "Bearer " + token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 실패 시 실행하는 메서드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		System.out.println("실패함");
		response.setStatus(401);
//	    response.getWriter().write("Authentication failed: " + failed.getMessage());
		
	}
	
}