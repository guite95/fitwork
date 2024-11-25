package com.fitwork.back.admin.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fitwork.back.admin.model.dto.VisitorStats;
import com.fitwork.back.admin.model.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	private final AdminRepository adminRepository;
	public AdminServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Override
	public void updateVisitorCount() {
        Map<String, Object> params = new HashMap<>();
        params.put("visitDate", LocalDate.now());
        adminRepository.insertOrUpdateVisitorCount(params);
    }

    @Override
	public List<VisitorStats> getDailyStats() {
        List<VisitorStats> stats = adminRepository.getDailyStats();
        return stats.stream()
                .map((VisitorStats stat) -> new VisitorStats(stat.getVisitDate().formatted(DATE_FORMATTER), stat.getVisitorCount()))
                .collect(Collectors.toList());
    }

    @Override
	public List<VisitorStats> getWeeklyStats() {
        List<VisitorStats> stats = adminRepository.getWeeklyStats();
        return stats.stream()
                .map((VisitorStats stat) -> new VisitorStats(stat.getVisitDate().formatted(DATE_FORMATTER), stat.getVisitorCount()))
                .collect(Collectors.toList());
    }

    @Override
	public List<VisitorStats> getMonthlyStats() {
        List<VisitorStats> stats = adminRepository.getMonthlyStats();
        return stats.stream()
                .map((VisitorStats stat) -> new VisitorStats(stat.getVisitDate().formatted(DATE_FORMATTER), stat.getVisitorCount()))
                .collect(Collectors.toList());
    }

    // 방문자 수 추가(관리자용 수동)
    @Override
	public void addVisitorCount(VisitorStats visitorStats) {
        Map<String, Object> params = new HashMap<>();
        params.put("visitDate", LocalDate.parse(visitorStats.getVisitDate()));
        params.put("visitorCount", visitorStats.getVisitorCount());
        adminRepository.insertOrUpdateVisitorCount(params);
    }
}
