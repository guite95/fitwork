package com.fitwork.back.member.model.service;

import com.fitwork.back.member.model.dto.Member;

public interface MemberService {
	
	/**
	 * 회원가입
	 * @param member
	 * @return
	 */
	public boolean joinMember(Member member);
	
	/**
	 * 회원 정보 조회
	 * @param id
	 * @return
	 */
	public Member getMemberInfo(String id);
	
	/**
	 * 로그인 체크
	 * @param id
	 * @param password
	 * @return
	 */
	public Member loginCheck(String id, String password);
	
	/**
	 * 회원 탈퇴
	 * @param id
	 * @return
	 */
	public boolean deleteMember(String id);
	
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return
	 */
	public boolean modifyMemberInfo(Member member);
	
}
