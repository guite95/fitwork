package com.fitwork.back.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private int commentNo;
	private String writer;
	private String content;
	private String regDate;
	private int boardNo;
	private int parentCommentNumber; // 기본댓글 : 0, 대댓글 : 부모댓글번호, 삭제된댓글 : -1
}
