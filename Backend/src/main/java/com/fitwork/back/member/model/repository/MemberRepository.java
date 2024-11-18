package com.fitwork.back.member.model.repository;

import com.fitwork.back.member.model.dto.Member;

public interface MemberRepository {
	/**
	 * 회원 등록
	 * @param member
	 */
	public void insertMember(Member member);
	
	/**
	 * 회원 존재하는지 확인
	 * @param id
	 * @return
	 */
	public int isMemberExist(String id);
	
	/**
	 * 전체 회원 조회
	 */
	public void selectAllMember();
	
	/**
	 * 회원 상세 조회
	 * @param id
	 * @return
	 */
	public Member selectMemberById(String id);
	
	/**
	 * 회원 정보 삭제
	 * @param id
	 */
	public void deleteMember(String id);
	
	/**
	 * 회원 정보 수정
	 * @param id
	 */
	public void updateMemberInfo(Member member);
	
}