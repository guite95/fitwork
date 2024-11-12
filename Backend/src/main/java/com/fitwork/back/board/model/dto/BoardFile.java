package com.fitwork.back.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardFile {
	private int fileNo;
	private String oriName;
	private String systemName;
	private String path;
	private int boardNo;
}
