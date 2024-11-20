package com.fitwork.back.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitwork.back.member.model.dto.Member;
import com.fitwork.back.member.model.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.memberRepository = memberRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public boolean joinMember(Member member) {
		System.out.println("회원가입 서비스 진입 확인");
		String id = member.getId();
		System.out.println("아이디 확인");
		if (memberRepository.isMemberExist(id)) {
			member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
			
			memberRepository.insertMember(member);
			return true;
		}
		
		return false;
	}

	@Override
	public Member getMemberInfo(String id) {
		return memberRepository.selectMemberById(id);
	}

	@Override
	public boolean deleteMember(String id) {
		if (memberRepository.isMemberExist(id)) {
	        memberRepository.deleteMember(id);
	        return true;
	    }
	    return false;
	}

	@Override
	public boolean modifyMemberInfo(Member member) {
		if (memberRepository.isMemberExist(member.getId())) {
	        memberRepository.updateMemberInfo(member);
	        return true;
	    }
	    return false;
	}

}
