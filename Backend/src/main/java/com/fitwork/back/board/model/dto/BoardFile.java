package com.fitwork.back.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardFile {
	private int fileNo;
	private String oriName;
	private String systemName;
	private String path;
	private int boardNo;
}
