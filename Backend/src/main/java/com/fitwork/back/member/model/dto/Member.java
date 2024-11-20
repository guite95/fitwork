package com.fitwork.back.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Member {
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String phoneNumber;
	private String address;
	private String memberRole;
}
