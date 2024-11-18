package com.fitwork.back._class.model.repository;

import java.util.List;

import com.fitwork.back._class.model.dto._Class;

public interface _ClassRepository {
	// 클래스 조회 : 전체 조회, 상세 조회, 지역별 조회, 성별 조회, 신청한 목록 조회, 가입된 목록 조회, 내가 개설한 강좌
	
	/**
	 * 전체 클래스 조회
	 * @return
	 */
	public List<_Class> selectAllClass();
	
	/**
	 * 클래스 상세 조회
	 * @param classNo
	 * @return
	 */
	public List<_Class> selectClassByClassNo(int classNo);
	
	/**
	 * 위치별 클래스 조회
	 * @param location
	 * @return
	 */
	public List<_Class> selectClassByLocation(String location);
	
	/**
	 * 성별 비중 순으로 클래스 조회
	 * @param gender
	 * @return
	 */
	public List<_Class> selectClassByGender(String gender);
	
	/**
	 * 회원이 신청한 클래스 조회
	 * @param id
	 * @return
	 */
	public List<_Class> selectRegistedClassById(String id); 
	
	/**
	 * 회원이 가입된 클래스 조회
	 * @param id
	 * @return
	 */
	public List<_Class> selectMemberedClassById(String id);
	
	/**
	 * 강사가 개설한 클래스 조회
	 * @param leader
	 * @return
	 */
	public List<_Class> selectClassByLeader(String leader);
	
	public void insertClass(_Class _class);
	
	public void updateClassInfo(_Class _class);
	
	public void deleteClass(int classNo);
	
}
