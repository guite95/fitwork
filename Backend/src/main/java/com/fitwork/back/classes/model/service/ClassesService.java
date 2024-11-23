package com.fitwork.back.classes.model.service;

import java.util.List;

import com.fitwork.back.classes.model.dto.Classes;

public interface ClassesService {
	
	/**
	 * 전체 클래스 목록 조회
	 * @return
	 */
	public List<Classes> classList();
	
	/**
	 * 클래스 정보 상세 조회
	 * @param classNo
	 * @return
	 */
	public Classes classDetail(int classNo);
	
	/**
	 * list가 비어있는지 체크
	 * @param list
	 * @return
	 */
	public String checkIfClassIsEmpty(List<Classes> list);
	
	/**
	 * 위치별 클래스 목록 조회
	 * @param location
	 * @return
	 */
	public List<Classes> classByLocation(String location);
	
	/**
	 * 카테고리별 클래스 목록 조회
	 * @param category
	 * @return
	 */
	public List<Classes> classByCategory(String category);
	
	/**
	 * 성별 비중 클래스 목록 조회
	 * @param gender
	 * @return
	 */
	public List<Classes> classByGender(String gender);
	
	/**
	 * 회원이 신청한 클래스 목록
	 * @param id
	 * @return
	 */
	public List<Classes> registerdClass(String id);
	
	/**
	 * 회원이 수강중인 클래스 목록
	 * @param id
	 * @return
	 */
	public List<Classes> memberedClass(String id);
	
	/**
	 * 강사가 개설한 클래스 목록
	 * @param leader
	 * @return
	 */
	public List<Classes> leaderedClass(String leader);
	
	/**
	 * 클래스 정보 등록
	 * @param classes
	 */
	public void registClass(Classes classes);
	
	/**
	 * 클래스에 수강 신청
	 * @param id
	 * @param classNo
	 */
	public void addToRegistedClass(String id, int classNo);
	
	/**
	 * 클래스 등록 완료
	 * @param id
	 * @param classNo
	 */
	public void permitMemberToClass(String id, int classNo);
	
	/**
	 * 클래스 탈퇴
	 * @param id
	 * @param classNo
	 */
	public void exitFromClass(String id, int classNo);
	
	/**
	 * 클래스 수강 거절
	 * @param id
	 * @param classNo
	 */
	public void refuseRegist(String id, int classNo);
	
	/**
	 * 클래스 정보 수정
	 * @param classes
	 */
	public void modifyClassInfo(Classes classes);
	
	public void deleteClassFile(int fileNo);
	
	/**
	 * 클래스 정보 삭제
	 * @param classNo
	 */
	public void deleteClassInfo(int classNo);
	
}
