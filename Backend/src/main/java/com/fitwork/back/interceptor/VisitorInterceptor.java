package com.fitwork.back.interceptor;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fitwork.back.admin.model.service.AdminService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class VisitorInterceptor implements HandlerInterceptor {

    private static final String VISITOR_COOKIE_NAME = "visitor";
    private static final int COOKIE_MAX_AGE_SECONDS = 3 * 60 * 60; // 3시간

    private final AdminService adminService;
    public VisitorInterceptor(AdminService adminService) {
    	this.adminService = adminService;
	}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 쿠키 체크
        Cookie[] cookies = request.getCookies();
        boolean isRevisit = false;

        if (cookies != null) {
            isRevisit = Arrays.stream(cookies)
                    .anyMatch(cookie -> VISITOR_COOKIE_NAME.equals(cookie.getName()));
        }

        // 방문자 수 증가 (재방문이 아닐 경우에만)
        if (!isRevisit) {
            adminService.updateVisitorCount();
            // 새로운 쿠키 생성 (3시간 동안 유효)
            Cookie visitorCookie = new Cookie(VISITOR_COOKIE_NAME, String.valueOf(Instant.now().toEpochMilli()));
            visitorCookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
            visitorCookie.setHttpOnly(true); // JavaScript에서 접근 불가
            visitorCookie.setPath("/"); // 전체 도메인에서 유효
            response.addCookie(visitorCookie);
        }

        return true; // 다음 핸들러로 요청을 전달
    }
}
