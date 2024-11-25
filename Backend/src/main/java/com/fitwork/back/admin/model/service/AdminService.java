package com.fitwork.back.admin.model.service;

import java.util.List;

import com.fitwork.back.admin.model.dto.VisitorStats;

public interface AdminService {

	void updateVisitorCount();

	List<VisitorStats> getDailyStats();

	List<VisitorStats> getWeeklyStats();

	List<VisitorStats> getMonthlyStats();

	// 방문자 수 추가
	void addVisitorCount(VisitorStats visitorStats);

}