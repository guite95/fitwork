package com.fitwork.back.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {
//	private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

	// 12.3버전
    //accessToken 만료시간 설정
    public static final long ACCESS_TOKEN_VALIDATION_SECOND = 1000L*60*60; //1시간
    public static final String AUTHORIZATION_HEADER = "Authorization"; //헤더 이름

    // SecretKey 생성
    private SecretKey secretKey;
	public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}
 	
 	public String getId(String token) {
 		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", String.class);
 	}
 	
 	public String getRole(String token) {
 		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
 	}
 	
 	public Boolean isExpired(String token) {
 		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
 	}
 	
 	public String createJwt(String id, String role) {
 		return Jwts.builder()
 				.claim("id", id)
 				.claim("role", role)
 				.issuedAt(new Date(System.currentTimeMillis()))
 				.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND))
 				.signWith(secretKey)
 				.compact();
 	}

    // HttpServletRequest에서 Authorization Header를 통해 access token을 추출하는 메서드입니다.
    public String getAccessToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

//    public String determineRedirectURI(HttpServletRequest httpServletRequest, String memberURI, String nonMemberURI) {
//        String token = getAccessToken(httpServletRequest);
//        if (token == null) {
//            return nonMemberURI; // 비회원용 URI로 리다이렉트
//        } else {
//            return memberURI; // 회원용 URI로 리다이렉트
//        }
//    }
}
