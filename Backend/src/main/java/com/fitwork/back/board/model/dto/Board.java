package com.fitwork.back.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
