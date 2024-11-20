package com.fitwork.back.classes.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fitwork.back.classes.model.dto.Classes;
import com.fitwork.back.classes.model.dto.ClassesFile;

public interface ClassesRepository {
	// 클래스 조회 : 전체 조회, 상세 조회, 지역별 조회, 성별 조회, 신청한 목록 조회, 가입된 목록 조회, 내가 개설한 강좌
	
	/**
	 * 전체 클래스 조회
	 * @return
	 */
	public List<Classes> selectAllClass();
	
	/**
	 * 클래스 상세 조회
	 * @param classNo
	 * @return
	 */
	public Classes selectClassByClassNo(int classNo);
	
	/**
	 * 위치별 클래스 조회
	 * @param location
	 * @return
	 */
	public List<Classes> selectClassByLocation(String location);
	
	/**
	 * 카테고리별 클래스 조회
	 * @param category
	 * @return
	 */
	public List<Classes> selectClassByCategory(String category);
	
	/**
	 * 성별 비중 순으로 클래스 조회
	 * @param gender
	 * @return
	 */
	public List<Classes> selectClassByGender(String gender);
	
	/**
	 * 회원이 신청한 클래스 조회
	 * @param id
	 * @return
	 */
	public List<Classes> selectRegistedClassById(String id); 
	
	/**
	 * 회원이 가입된 클래스 조회
	 * @param id
	 * @return
	 */
	public List<Classes> selectMemberedClassById(String id);
	
	/**
	 * 강사가 개설한 클래스 조회
	 * @param leader
	 * @return
	 */
	public List<Classes> selectClassByLeader(String leader);
	
	/**
	 * 클래스 정보 등록
	 * @param classes
	 */
	public void insertClass(Classes classes);
	
	/**
	 * 클래스 파일 정보 저장
	 * @param classesFile
	 */
	public void insertClassFile(ClassesFile classesFile);
	
	/**
	 * 클래스 신청 테이블에 저장
	 * @param id
	 * @param classNo
	 */
	public void insertClassRegist(String id, int classNo);
	
	/**
	 * 클래스 신청 목록에서 삭제
	 * @param nickname
	 * @param classNo
	 */
	public void deleteClassRegist(String id, int classNo);
	
	/**
	 * 클래스 수강인원으로 등록
	 * @param nickname
	 * @param classNo
	 */
	public void insertClassMember(String id, int classNo);
	
	/**
	 * 클래스 수강인원에서 삭제
	 * @param nickname
	 * @param classNo
	 */
	public void deleteClassMember(String id, int classNo);
	
	/**
	 * 클래스 정보 수정
	 * @param classes
	 */
	public void updateClassInfo(Classes classes);
	
	/**
	 * 클래스 정보 삭제
	 * @param classNo
	 */
	public void deleteClass(int classNo);
	
}
