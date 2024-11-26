package com.fitwork.back.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	String id;
	String password;

	public LoginDTO(Member member) {
		this.id = member.getId();
		this.password = member.getPassword();
	}
}