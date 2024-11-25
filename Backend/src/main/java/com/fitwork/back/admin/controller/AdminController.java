package com.fitwork.back.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitwork.back.admin.model.dto.VisitorStats;
import com.fitwork.back.admin.model.service.AdminService;

@RestController
@RequestMapping("api-admin")
public class AdminController {
	private final AdminService adminService;
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	// 일별 방문자 통계 조회
    @GetMapping("/daily")
    public ResponseEntity<List<VisitorStats>> getDailyStats() {
        List<VisitorStats> stats = adminService.getDailyStats();
        return ResponseEntity.ok(stats);
    }

    // 주별 방문자 통계 조회
    @GetMapping("/weekly")
    public ResponseEntity<List<VisitorStats>> getWeeklyStats() {
        List<VisitorStats> stats = adminService.getWeeklyStats();
        return ResponseEntity.ok(stats);
    }

    // 월별 방문자 통계 조회
    @GetMapping("/monthly")
    public ResponseEntity<List<VisitorStats>> getMonthlyStats() {
        List<VisitorStats> stats = adminService.getMonthlyStats();
        return ResponseEntity.ok(stats);
    }

    // 새로운 방문자 수 추가 (관리자가 수동으로 데이터를 추가할 때 사용)
    @PostMapping("/add")
    public ResponseEntity<String> addVisitorCount(@RequestBody VisitorStats visitorStatsDTO) {
        try {
            adminService.addVisitorCount(visitorStatsDTO);
            return ResponseEntity.ok("Visitor count added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while adding visitor count: " + e.getMessage());
        }
    }
}
