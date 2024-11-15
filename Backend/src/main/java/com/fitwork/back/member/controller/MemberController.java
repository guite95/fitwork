package com.fitwork.back.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitwork.back.member.model.dto.LoginDTO;
import com.fitwork.back.member.model.dto.Member;
import com.fitwork.back.member.model.service.MemberService;
import com.fitwork.back.util.JWTUtil;

@RestController
@RequestMapping("/api-member")
public class MemberController {
	
	private final AuthenticationManager authenticationManager;
	private final MemberService memberService;
	private final JWTUtil jwtUtil;
	public MemberController(AuthenticationManager authenticationManager, MemberService memberService, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/regist/general")
	public ResponseEntity<String> generalRegist(@RequestBody Member member) {
		member.setMemberRole("ROLE_GENERAL");
		
		if (memberService.joinMember(member)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("회원가입을 축하합니다!");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주십시오");
	}
	
	@PostMapping("/regist/instructor")
	public ResponseEntity<String> instructorRegist(@RequestBody Member member) {
		member.setMemberRole("ROLE_INSTRUCTOR");
		
		if (memberService.joinMember(member)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("환영합니다 강사님");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주십시오");
	}
	
}
