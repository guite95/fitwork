package com.fitwork.back.club.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubFile {
	private int fileNo;
	private String oriName;
	private String systemName;
	private String path;
	private int boardNo;
}
