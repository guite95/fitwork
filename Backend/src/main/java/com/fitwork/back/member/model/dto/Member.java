package com.fitwork.back.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String gender;
	private String birthDate;
	private int age;
	private String email;
	private String phoneNumber;
	private String address;
	private String memberRole;
}
