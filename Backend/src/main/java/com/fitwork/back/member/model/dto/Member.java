package com.fitwork.back.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
