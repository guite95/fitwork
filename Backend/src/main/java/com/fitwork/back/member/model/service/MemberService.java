package com.fitwork.back.member.model.service;

import com.fitwork.back.member.model.dto.Member;

public interface MemberService {
	
	public boolean joinMember(Member member);
	
	public Member getMemberInfo(String id);
	
	public boolean deleteMember(String id);
	
	public boolean modifyMemberInfo(Member member);
	
}
