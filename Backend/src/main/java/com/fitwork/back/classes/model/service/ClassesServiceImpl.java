package com.fitwork.back.classes.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitwork.back.classes.model.dto.Classes;
import com.fitwork.back.classes.model.dto.ClassesFile;
import com.fitwork.back.classes.model.repository.ClassesRepository;
import com.fitwork.back.clubs.model.dto.ClubFile;

@Service
public class ClassesServiceImpl implements ClassesService {
	
	private final ClassesRepository classesRepository;
	public ClassesServiceImpl(ClassesRepository classesRepository) {
		this.classesRepository = classesRepository;
	}

	@Override
	public List<Classes> classList() {
		List<Classes> list = classesRepository.selectAllClass();
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public Classes classDetail(int classNo) {
		Classes classes = classesRepository.selectClassByClassNo(classNo);
		
		ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(classNo);
		
		if (classesFile != null) classes.setClassesFile(classesFile);
		
		return classes;
	}

	@Override
	public List<Classes> classByLocation(String location) {
		List<Classes> list = classesRepository.selectClassByLocation(location);
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public List<Classes> classByCategory(String category) {
		List<Classes> list = classesRepository.selectClassByCategory(category);
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public List<Classes> classByGender(String gender) {
		List<Classes> list = classesRepository.selectClassByGender(gender);
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public List<Classes> registerdClass(String id) {
		List<Classes> list = classesRepository.selectRegistedClassById(id);
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public List<Classes> memberedClass(String id) {
		List<Classes> list = classesRepository.selectMemberedClassById(id);
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public List<Classes> leaderedClass(String leader) {
		List<Classes> list = classesRepository.selectClassByLeader(leader);
		for (int i = 0; i < list.size(); i++) {
        	ClassesFile classesFile = classesRepository.selectClassesFileByClassNo(list.get(i).getClassNo());
        	if (classesFile != null) list.get(i).setClassesFile(classesFile);
        }
        return list;
	}

	@Override
	public void registClass(Classes classes) {
		classesRepository.insertClass(classes);
		
		ClassesFile classesFile = classes.getClassesFile();
		if (classesFile != null && classesFile.getOriName() != null && !classesFile.getOriName().isEmpty()) {
			classesFile.setClassNo(classes.getClassNo());
			
			classesRepository.insertClassFile(classesFile);
		}
	}
	
	@Override
	public void addToRegistedClass(String id, int classNo) {
		classesRepository.insertClassRegist(id, classNo);
	}
	
	@Override
	public boolean isRegisted(String id, int classNo) {
		return classesRepository.isRegisted(id, classNo);
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
		classes.getClassesFile().setClassNo(classes.getClassNo());
		classesRepository.insertClassFile(classes.getClassesFile());
		classesRepository.updateClassInfo(classes);
	}
	
	@Override
	public void deleteClassFile(int fileNo) {
		classesRepository.deleteClassFile(fileNo);
	}

	@Override
	public void deleteClassInfo(int classNo) {
		classesRepository.deleteClass(classNo);
	}

	@Override
	public String checkIfClassIsEmpty(List<Classes> list) {
		if (list.isEmpty()) {
            return "클래스가 없습니다";
        }
        return null;
	}

}
