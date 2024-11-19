package com.fitwork.back.classes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitwork.back.classes.model.dto.Classes;
import com.fitwork.back.classes.model.service.ClassesService;

@RestController
@RequestMapping("/api-class")
public class ClassesController {
	
	private final ClassesService classesService;
	public ClassesController(ClassesService classesService) {
		this.classesService = classesService;
	}
	
	@GetMapping("/list/all")
	public ResponseEntity<Object> getClassesList() {
		try {
			List<Classes> list = classesService.classList();
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("{classNo}")
	public ResponseEntity<Object> getClassesDetail(@PathVariable int classNo) {
		try {
			Classes classes = classesService.classDetail(classNo);
			
			if (classes == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(classes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("/location/{location}")
	public ResponseEntity<Object> getNearbyClasses(@PathVariable String location) {
		try {
			List<Classes> list = classesService.classByLocation(location);
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<Object> getCategoryClasses(@PathVariable String category) {
		try {
			List<Classes> list = classesService.classByCategory(category);
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("/gender/{gender}")
	public ResponseEntity<Object> getGenderClasses(@PathVariable String gender) {
		try {
			List<Classes> list = classesService.classByGender(gender);
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("/registed/{id}")
	public ResponseEntity<Object> getRegistedClasses(@PathVariable String id) {
		try {
			List<Classes> list = classesService.registerdClass(id);
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("/membered/{id}")
	public ResponseEntity<Object> getMemberedClasses(@PathVariable String id) {
		try {
			List<Classes> list = classesService.memberedClass(id);
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
	@GetMapping("/leader/{leader}")
	public ResponseEntity<Object> getLeaderedClasses(@PathVariable String leader) {
		try {
			List<Classes> list = classesService.leaderedClass(leader);
			
			if (list.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
		}
	}
	
}
