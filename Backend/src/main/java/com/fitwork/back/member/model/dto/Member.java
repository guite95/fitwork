package com.fitwork.back.member.model.dto;

public class Member {
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String phoneNumber;
	private String address;
	private int instructor;
	public Member() {}
	public Member(String id, String password, String name, String nickname, String email, String phone_number,
			String address, int instructor) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.phoneNumber = phone_number;
		this.address = address;
		this.instructor = instructor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phone_number) {
		this.phoneNumber = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getInstructor() {
		return instructor;
	}
	public void setInstructor(int instructor) {
		this.instructor = instructor;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", password=" + password + ", name=" + name + ", nickname=" + nickname + ", email="
				+ email + ", phoneNumber=" + phoneNumber + ", address=" + address + ", instructor=" + instructor
				+ "]";
	}
}
