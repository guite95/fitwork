package com.fitwork.back.board.model.repository;

import java.util.List;

import com.fitwork.back.board.model.dto.Board;
import com.fitwork.back.board.model.dto.BoardFile;
import com.fitwork.back.board.model.dto.BoardSearch;
import com.fitwork.back.board.model.dto.Comment;

public interface BoardRepository {
	
	/**
	 * 게시물 등록
	 * @param board
	 */
	public void insertBoard(Board board);
	
	/**
	 * 게시물 파일 등록
	 * @param boardFile
	 */
	public void insertFile(BoardFile boardFile);
	
	/**
	 * 전체 조회(검색, 페이징 포함)
	 * @param boardSearch
	 * @return
	 */
	public List<Board> selectAllBoard(BoardSearch boardSearch);
	
	/**
	 * 페이징 처리를 위한 전체 게시물 수 반환
	 * @param boardSearch
	 * @return
	 */
	public int selectBoardCount(BoardSearch boardSearch);
	
	/**
	 * 게시물 상세 보기
	 * @param boardNo
	 * @return
	 */
	public Board selectBoardByNo(int boardNo);
	
	/**
	 * 게시물 파일 선택
	 * @param boardNo
	 * @return
	 */
	public BoardFile selectBoardFileByNo(int boardNo);
	
	/**
	 * 게시물 조회수 업데이트
	 * @param boardNo
	 */
	public void updateViewCnt(int boardNo);
	
	/**
	 * 게시물 좋아요 수 증가
	 * @param boardNo
	 */
	public void increaseLikeCnt(int boardNo);
	
	/**
	 * 좋아요 테이블에 추가
	 * @param id
	 * @param boardNo
	 */
	public void insertLike(String id, int boardNo);
	
	/**
	 * 좋아요 테이블에서 삭제
	 * @param id
	 * @param boardNo
	 */
	public void deleteLike(String id, int boardNo);
	
	/**
	 * 게시물 좋아요 수 감소
	 * @param boardNo
	 */
	public void decreaseLikeCnt(int boardNo);
	
	/**
	 * 게시물 삭제
	 * @param boardNo
	 */
	public void deleteBoard(int boardNo);
	
	/**
	 * 게시물 파일 삭제
	 * @param fileNo
	 */
	public void deleteBoardFile(int fileNo);
	
	/**
	 * 게시물 수정
	 * @param board
	 */
	public void updateBoard(Board board);
	
	/**
	 * 댓글 등록
	 * @param comment
	 */
	public void insertComment(Comment comment);
	
	/**
	 * 게시물 댓글 조회
	 * @param boardNo
	 * @return
	 */
	public List<Comment> selectCommentByBoardNo(int boardNo);
	
	/**
	 * 내가 쓴 댓글 조회
	 * @param writer
	 * @return
	 */
	public List<Comment> selectCommentByWriter(String writer);
	
	/**
	 * 댓글 수정
	 * @param comment
	 */
	public void updateComment(Comment comment);
	
	/**
	 * 댓글 삭제
	 * @param commentNo
	 */
	public void deleteComment(int commentNo);
	
}
