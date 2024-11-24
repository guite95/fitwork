package com.fitwork.back.classes.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitwork.back.classes.model.dto.Classes;
import com.fitwork.back.classes.model.dto.ClassesFile;
import com.fitwork.back.classes.model.service.ClassesService;


@RestController
@RequestMapping("/api-class")
public class ClassesController {

	private final ClassesService classesService;

	public ClassesController(ClassesService classesService) {
		this.classesService = classesService;
	}

	/////////////////////////////////////////////////////////////////////////////////
	// 예외 처리 메서드
	private ResponseEntity<Object> handleException(Exception e) {
		e.printStackTrace();
		if (e instanceof IllegalArgumentException) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다 잠시 후 다시 시도해주세요");
	}
	
	// list부분 처리 메서드
	private ResponseEntity<Object> handleClassListResponse(List<Classes> list) {
	    String message = classesService.checkIfClassIsEmpty(list);
	    if (message != null) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of());
	    }
	    return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/list/all")
	public ResponseEntity<Object> getClassesList() {
		try {
			List<Classes> list = classesService.classList();

			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/detail/{classNo}")
	public ResponseEntity<Object> getClassesDetail(@PathVariable int classNo) {
		try {
			Classes classes = classesService.classDetail(classNo);

			if (classes == null)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클래스가 없습니다");
			return ResponseEntity.status(HttpStatus.OK).body(classes);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/list/location/{location}")
	public ResponseEntity<Object> getNearbyClasses(@PathVariable String location) {
		try {
			List<Classes> list = classesService.classByLocation(location);
			
			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/list/category/{category}")
	public ResponseEntity<Object> getCategoryClasses(@PathVariable String category) {
		try {
			List<Classes> list = classesService.classByCategory(category);

			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/list/gender/{gender}")
	public ResponseEntity<Object> getGenderClasses(@PathVariable String gender) {
		try {
			List<Classes> list = classesService.classByGender(gender);

			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/list/registed/{id}")
	public ResponseEntity<Object> getRegistedClasses(@PathVariable String id) {
		try {
			List<Classes> list = classesService.registerdClass(id);

			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/list/membered/{id}")
	public ResponseEntity<Object> getMemberedClasses(@PathVariable String id) {
		try {
			List<Classes> list = classesService.memberedClass(id);

			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@GetMapping("/list/leader/{leader}")
	public ResponseEntity<Object> getLeaderedClasses(@PathVariable String leader) {
		try {
			List<Classes> list = classesService.leaderedClass(leader);

			return handleClassListResponse(list);
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
    @PostMapping("/register/{id}/{classNo}")
    public ResponseEntity<Object> registerClass(@PathVariable String id, @PathVariable int classNo) {
        try {
            classesService.addToRegistedClass(id, classNo);
            return ResponseEntity.status(HttpStatus.CREATED).body("수강 신청이 완료되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/register/cancel/{id}/{classNo}")
    public ResponseEntity<Object> refuseClassRegistration(@PathVariable String id, @PathVariable int classNo) {
        try {
            classesService.refuseRegist(id, classNo);
            return ResponseEntity.status(HttpStatus.OK).body("수강 신청이 취소되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/member/exit/{id}/{classNo}")
    public ResponseEntity<Object> exitClass(@PathVariable String id, @PathVariable int classNo) {
        try {
            classesService.exitFromClass(id, classNo);
            return ResponseEntity.status(HttpStatus.OK).body("클래스에서 탈퇴하였습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping("/register/class")
    public ResponseEntity<Object> registClass(@RequestPart Classes classes, @RequestPart(required = false) MultipartFile file) {
        try {
        	if (file != null) {
        		String oriName = file.getOriginalFilename();
        		
        		if (oriName != null && oriName.length() > 0) {
        			SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH");
        			String subDir = sdf.format(new Date());
        			
        			File dir = new File("c://SSAFY/final-prj/classes/img" + subDir);
        			dir.mkdirs();
        			
        			String systemName = UUID.randomUUID().toString() + oriName;
        			
        			file.transferTo(new File(dir, systemName));
        			
        			ClassesFile classesFile = new ClassesFile();
        			classesFile.setPath(subDir);
        			classesFile.setOriName(oriName);
        			classesFile.setSystemName(systemName);
        			
        			classes.setClassesFile(classesFile);
        		}
        	}
            
            classesService.registClass(classes);
            return ResponseEntity.status(HttpStatus.CREATED).body("클래스가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/modify/{classNo}")
    public ResponseEntity<Object> modifyClass(@PathVariable int classNo, @RequestPart Classes classes, @RequestPart(required = false) MultipartFile file) {
        try {
        	Classes tmp = classesService.classDetail(classNo);
        	
        	if (file != null) {
        		if (tmp.getClassesFile() != null) {
        			classesService.deleteClassFile(tmp.getClassesFile().getFileNo());
        		}
        		String oriName = file.getOriginalFilename();
        		
        		if (oriName != null && oriName.length() > 0) {
        			SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH");
        			String subDir = sdf.format(new Date());
        			
        			File dir = new File("c://SSAFY/final-prj/classes/img" + subDir);
        			dir.mkdirs();
        			
        			String systemName = UUID.randomUUID().toString() + oriName;
        			
        			file.transferTo(new File(dir, systemName));
        			
        			ClassesFile classesFile = new ClassesFile();
        			classesFile.setPath(subDir);
        			classesFile.setOriName(oriName);
        			classesFile.setSystemName(systemName);
        			
        			tmp.setClassesFile(classesFile);
        		}
        	}
        	
        	tmp.setClassName(classes.getClassName());
        	tmp.setLocation(classes.getLocation());
        	tmp.setTag(classes.getTag());
        	tmp.setDescription(classes.getDescription());
        	tmp.setPrice(classes.getPrice());
            
            classesService.modifyClassInfo(tmp);
            return ResponseEntity.status(HttpStatus.OK).body("클래스 정보가 수정되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/delete/{classNo}")
    public ResponseEntity<Object> deleteClass(@PathVariable int classNo) {
        try {
            classesService.deleteClassInfo(classNo);
            return ResponseEntity.status(HttpStatus.OK).body("클래스가 삭제되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

}
