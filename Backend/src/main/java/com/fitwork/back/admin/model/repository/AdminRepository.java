package com.fitwork.back.admin.model.repository;

import java.util.List;
import java.util.Map;

import com.fitwork.back.admin.model.dto.VisitorStats;

public interface AdminRepository {
	void insertOrUpdateVisitorCount(Map<String, Object> params);
    List<VisitorStats> getDailyStats();
    List<VisitorStats> getWeeklyStats();
    List<VisitorStats> getMonthlyStats();
}
