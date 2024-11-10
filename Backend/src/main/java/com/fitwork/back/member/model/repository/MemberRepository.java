package com.fitwork.back.member.model.repository;

import com.fitwork.back.member.model.dto.Member;

public interface MemberRepository {
	
	public void insertMember(Member member);
	
	public int isMemberExist(String id);
	
	public void selectAllMember();
	
	public Member selectMemberById(String id);
	
	public void deleteMember(String id);
	
	public void updateMember(String id);
	
}