package com.fitwork.back.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitwork.back.member.model.dto.Member;
import com.fitwork.back.member.model.service.MemberService;
@RestController
@RequestMapping("/api-member")
public class MemberController {

	private final MemberService memberService;
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	/**
	 * 회원가입
	 * @param member
	 * @return
	 */
	@PostMapping("/regist")
	public ResponseEntity<String> generalRegist(@RequestBody Member member) {
		if (memberService.joinMember(member)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("회원가입을 축하합니다!");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주십시오");
	}
	
	/**
	 * 회원정보
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getMemberInfo(@PathVariable String id) {
		try {
			Member member = memberService.getMemberInfo(id);
			
			if (member == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원정보가 없습니다"); 
			
			return ResponseEntity.status(HttpStatus.OK).body(member);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주십시오");
		}
	}
	
	/**
	 * 회원정보 수정
	 * @param id
	 * @param member
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> modifyMember(@PathVariable String id, @RequestBody Member member) {
	    member.setId(id);
	    if (memberService.modifyMemberInfo(member)) {
	        return ResponseEntity.status(HttpStatus.OK).body("회원 정보가 수정되었습니다.");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보 수정에 실패했습니다.");
	}

	/**
	 * 회원정보 삭제
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMember(@PathVariable String id) {
	    if (memberService.deleteMember(id)) {
	        return ResponseEntity.status(HttpStatus.OK).body("회원이 삭제되었습니다.");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 삭제에 실패했습니다.");
	}

	@GetMapping("/check/{id}")
	public ResponseEntity<Boolean> isExistId(@PathVariable String id) {
		try {
			boolean isExist = memberService.isExistId(id);
			return ResponseEntity.status(HttpStatus.OK).body(isExist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}