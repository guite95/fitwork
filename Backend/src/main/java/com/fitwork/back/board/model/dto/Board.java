package com.fitwork.back.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int boardNo;
	private String title;
	private String writer;
	private String content;
	private String regDate;
	private int viewCnt;
	private int likeCnt;
	private boolean isModified;
	private BoardFile boardFile;
}
