package com.fitwork.back.clubs.controller;

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

import com.fitwork.back.clubs.model.dto.Club;
import com.fitwork.back.clubs.model.dto.ClubFile;
import com.fitwork.back.clubs.model.service.ClubService;

@RestController
@RequestMapping("/api-club")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
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
    private ResponseEntity<Object> handleClubListResponse(List<Club> list) {
        String message = clubService.checkIfClubIsEmpty(list);
        if (message != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    ////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/list/all")
    public ResponseEntity<Object> getClubList() {
        try {
            List<Club> list = clubService.clubList();

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/detail/{clubNo}")
    public ResponseEntity<Object> getClubDetail(@PathVariable int clubNo) {
        try {
            Club club = clubService.clubDetail(clubNo);

            if (club == null)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 클럽이 없습니다");
            return ResponseEntity.status(HttpStatus.OK).body(club);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/list/location/{location}")
    public ResponseEntity<Object> getNearbyClubs(@PathVariable String location) {
        try {
            List<Club> list = clubService.clubByLocation(location);

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/list/category/{category}")
    public ResponseEntity<Object> getCategoryClubs(@PathVariable String category) {
        try {
            List<Club> list = clubService.clubByCategory(category);

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/list/gender/{gender}")
    public ResponseEntity<Object> getGenderClubs(@PathVariable String gender) {
        try {
            List<Club> list = clubService.clubByGender(gender);

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/registed/{id}")
    public ResponseEntity<Object> getRegistedClubs(@PathVariable String id) {
        try {
            List<Club> list = clubService.registeredClub(id);

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/membered/{id}")
    public ResponseEntity<Object> getMemberedClubs(@PathVariable String id) {
        try {
            List<Club> list = clubService.memberedClub(id);

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/leader/{leader}")
    public ResponseEntity<Object> getLeaderedClubs(@PathVariable String leader) {
        try {
            List<Club> list = clubService.leaderedClub(leader);

            return handleClubListResponse(list);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping("/register/{id}/{clubNo}")
    public ResponseEntity<Object> registerClub(@PathVariable String id, @PathVariable int clubNo) {
        try {
            clubService.addToRegisteredClub(id, clubNo);
            return ResponseEntity.status(HttpStatus.CREATED).body("가입 신청이 완료되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/register/refuse/{id}/{clubNo}")
    public ResponseEntity<Object> refuseClubRegistration(@PathVariable String id, @PathVariable int clubNo) {
        try {
            clubService.refuseRegister(id, clubNo);
            return ResponseEntity.status(HttpStatus.OK).body("가입 신청이 거절되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/member/exit/{id}/{clubNo}")
    public ResponseEntity<Object> exitClub(@PathVariable String id, @PathVariable int clubNo) {
        try {
            clubService.exitFromClub(id, clubNo);
            return ResponseEntity.status(HttpStatus.OK).body("클럽에서 탈퇴하였습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping("/register/club")
    public ResponseEntity<Object> registClub(@RequestPart Club club, @RequestPart(required = false) MultipartFile file) {
        try {
        	if (file != null) {
        		String oriName = file.getOriginalFilename();
        		
        		if (oriName != null && oriName.length() > 0) {
        			SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH");
        			String subDir = sdf.format(new Date());
        			
        			File dir = new File("c://SSAFY/final-prj/clubs/img" + subDir);
        			dir.mkdirs();
        			
        			String systemName = UUID.randomUUID().toString() + oriName;
        			
        			file.transferTo(new File(dir, systemName));
        			
        			ClubFile clubFile = new ClubFile();
        			clubFile.setPath(subDir);
        			clubFile.setOriName(oriName);
        			clubFile.setSystemName(systemName);
        			
        			club.setClubFile(clubFile);
        		}
        	}

            clubService.registClub(club);
            return ResponseEntity.status(HttpStatus.CREATED).body("클럽이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/modify/{clubNo}")
    public ResponseEntity<Object> modifyClub(@PathVariable int clubNo, @RequestPart Club club, @RequestPart(required = false) MultipartFile file) {
        try {
        	Club tmp = clubService.clubDetail(clubNo);
        	
        	if (file != null) {
        		if (tmp.getClubFile() != null) {
        			clubService.deleteClubFile(tmp.getClubFile().getFileNo());
        		}
        		
        		String oriName = file.getOriginalFilename();
        		
        		if (oriName != null && oriName.length() > 0) {
        			SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH");
        			String subDir = sdf.format(new Date());
        			
        			File dir = new File("c://SSAFY/final-prj/clubs/img" + subDir);
        			dir.mkdirs();
        			
        			String systemName = UUID.randomUUID().toString() + oriName;
        			
        			file.transferTo(new File(dir, systemName));
        			
        			ClubFile clubFile = new ClubFile();
        			clubFile.setPath(subDir);
        			clubFile.setOriName(oriName);
        			clubFile.setSystemName(systemName);
        			
        			tmp.setClubFile(clubFile);
        		}
        		
        	}
        	
        	tmp.setClubName(club.getClubName());
        	tmp.setLocation(club.getLocation());
        	tmp.setTag(club.getTag());
        	tmp.setDescription(club.getDescription());
        	
            clubService.modifyClubInfo(tmp);
            return ResponseEntity.status(HttpStatus.OK).body("클럽 정보가 수정되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/delete/{clubNo}")
    public ResponseEntity<Object> deleteClub(@PathVariable int clubNo) {
        try {
            clubService.deleteClubInfo(clubNo);
            return ResponseEntity.status(HttpStatus.OK).body("클럽이 삭제되었습니다.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

}