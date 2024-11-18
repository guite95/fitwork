package com.fitwork.back._class.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class _ClassFile {
	private int fileNo;
	private String oriName;
	private String systemName;
	private String path;
	private int boardNo;
}
