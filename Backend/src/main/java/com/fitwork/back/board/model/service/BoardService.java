package com.fitwork.back.board.model.service;

import java.util.List;
import java.util.Map;

import com.fitwork.back.board.model.dto.Board;
import com.fitwork.back.board.model.dto.BoardSearch;
import com.fitwork.back.board.model.dto.Comment;

public interface BoardService {
	/**
	 * 게시글 등록
	 * @param board
	 */
	public void addBoard(Board board);
	
	/**
	 * 전체 게시글 조회
	 * @param boardSearch
	 * @return
	 */
	public List<Board> list(BoardSearch boardSearch);
	
	/**
	 * 게시글 상세 조회
	 * @param boardNo
	 * @return
	 */
	public Board boardDetail(int boardNo);
	
	/**
	 * 좋아요 수 증가
	 * @param id
	 * @param boardNo
	 */
	public void increseLikeCnt(String id, int boardNo);
	
	/**
	 * 좋아요 수 감소
	 * @param id
	 * @param boardNo
	 */
	public void decreaseLikeCnt(String id, int boardNo);
	
	/**
	 * 게시글 삭제
	 * @param boardNo
	 */
	public void deleteBoard(int boardNo);
	
	/**
	 * 게시글 수정
	 * @param board
	 */
	public void modifyBoard(Board board);
	
	/**
	 * 수정 등을 위한 게시글 1개 선택
	 * @param boardNo
	 * @return
	 */
	public Board selectOne(int boardNo);
	
	/**
	 * 파일 1개 삭제
	 * @param fileNo
	 */
	public void deleteFile(int fileNo);
	
	/**
	 * 댓글 등록
	 * @param comment
	 */
	public void addComment(Comment comment);
	
	/**
	 * 게시글 댓글 조회
	 * @param boardNo
	 * @return
	 */
	public List<Comment> commentList(int boardNo);
	
	/**
	 * 유저가 쓴 댓글 조회
	 * @param writer
	 * @return
	 */
	public List<Comment> userComment(String writer);
	
	/**
	 * 댓글 수정
	 * @param comment
	 */
	public void modifyComment(Comment comment);
	
	/**
	 * 댓글 삭제
	 * @param commentNo
	 */
	public void deleteComment(int commentNo);

	boolean isLiked(String id, int boardNo);
}
