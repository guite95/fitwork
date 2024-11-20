package com.fitwork.back.member.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.fitwork.back.member.model.dto.CustomUserDetails;
import com.fitwork.back.member.model.dto.Member;
import com.fitwork.back.member.model.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	public CustomUserDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		Member userData = memberRepository.selectMemberById(id);

		if (userData != null) {
			return new CustomUserDetails(userData);
		}

		return null;
	}
}