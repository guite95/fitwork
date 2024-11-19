package com.fitwork.back.classes.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitwork.back.classes.model.dto.Classes;
import com.fitwork.back.classes.model.repository.ClassesRepository;

@Service
public class ClassesServiceImpl implements ClassesService {
	
	private final ClassesRepository classesRepository;
	public ClassesServiceImpl(ClassesRepository classesRepository) {
		this.classesRepository = classesRepository;
	}

	@Override
	public List<Classes> classList() {
		return classesRepository.selectAllClass();
	}

	@Override
	public Classes classDetail(int classNo) {
		return classesRepository.selectClassByClassNo(classNo);
	}

	@Override
	public List<Classes> classByLocation(String location) {
		return classesRepository.selectClassByLocation(location);
	}

	@Override
	public List<Classes> classByCategory(String category) {
		return classesRepository.selectClassByCategory(category);
	}

	@Override
	public List<Classes> classByGender(String gender) {
		return classesRepository.selectClassByGender(gender);
	}

	@Override
	public List<Classes> registerdClass(String id) {
		return classesRepository.selectRegistedClassById(id);
	}

	@Override
	public List<Classes> memberedClass(String id) {
		return classesRepository.selectMemberedClassById(id);
	}

	@Override
	public List<Classes> leaderedClass(String leader) {
		return classesRepository.selectClassByLeader(leader);
	}

	@Override
	public void registClass(Classes classes) {
		classesRepository.insertClass(classes);
	}

	@Override
	public void addToRegistedClass(String id, int classNo) {
		classesRepository.insertClassRegist(id, classNo);
	}

	@Override
	public void permitMemberToClass(String id, int classNo) {
		classesRepository.deleteClassRegist(id, classNo);
		classesRepository.insertClassMember(id, classNo);
	}

	@Override
	public void exitFromClass(String id, int classNo) {
		classesRepository.deleteClassMember(id, classNo);
	}

	@Override
	public void refuseRegist(String id, int classNo) {
		classesRepository.deleteClassRegist(id, classNo);
	}

	@Override
	public void modifyClassInfo(Classes classes) {
		classesRepository.updateClassInfo(classes);
	}

	@Override
	public void deleteClassInfo(int classNo) {
		classesRepository.deleteClass(classNo);
	}

}
