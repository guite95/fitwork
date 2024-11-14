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
@RequestMapping /* ("/member") */
public class MemberController {
	
	private final AuthenticationManager authenticationManager;
	private final MemberService memberService;
	private final JWTUtil jwtUtil;
	public MemberController(AuthenticationManager authenticationManager, MemberService memberService, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping /* ("/regist/general") */("/join")
	public ResponseEntity<String> generalRegist(@RequestBody Member member) {
		member.setMemberRole("ROLE_GENERAL");
		
		if (memberService.joinMember(member)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("회원가입을 축하합니다!");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주십시오");
	}
	
//	@PostMapping("/regist/instructor")
//	public ResponseEntity<String> instructorRegist(@RequestBody Member member) {
//		member.setMemberRole("ROLE_INSTRUCTOR");
//		
//		if (memberService.joinMember(member)) {
//			return ResponseEntity.status(HttpStatus.CREATED).body("환영합니다 강사님");
//		}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주십시오");
//	}
	
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
//		System.out.println(loginDTO.getId());
//		System.out.println(loginDTO.getPassword());
//		
//		try {
//			// 사용자 인증
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							loginDTO.getId(), 
//							loginDTO.getPassword()
//					)
//			);
//			
//			Member member = memberService.loginCheck(loginDTO.getId(), loginDTO.getPassword());
//			
//			// JWT 발급
//			String jwt = jwtUtil.createJwt(member.getId(), member.getMemberRole());
//			
//			// 발급된 토큰을 반환
//			return ResponseEntity.status(HttpStatus.OK).body(jwt);
//		} catch (AuthenticationException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다");
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 에러가 발생했습니다. 잠시 후 다시 시도해주십시오");
//		}
//		
//	}
	
}
