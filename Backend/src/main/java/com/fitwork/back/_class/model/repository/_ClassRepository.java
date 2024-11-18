package com.fitwork.back._class.model.repository;

import java.util.List;

import com.fitwork.back._class.model.dto._Class;

public interface _ClassRepository {
	
	public void insertClass(_Class _class);
	
	// 클래스 조회 : 전체 조회, 상세 조회, 지역별 조회, 성별 조회, 신청한 목록 조회, 가입된 목록 조회, 내가 개설한 강좌
	
	public List<_Class> selectAllClass();
	
	public List<_Class> selectClassByClassNo(int classNo);
	
	public List<_Class> selectClassByLocation(String location);
	
//	public List<_Class> selectClassBy
	
//	public List<_Class> 
	
//	public List<_Class> 
	
	public List<_Class> selectClassByLeader(String leader);
	
}
