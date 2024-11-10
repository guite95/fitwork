package com.fitwork.back.board.model.repository;

import java.util.List;

import com.fitwork.back.board.model.dto.Board;
import com.fitwork.back.board.model.dto.BoardFile;
import com.fitwork.back.board.model.dto.BoardSearch;

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
	 * @param fileNo
	 * @return
	 */
	public BoardFile selectBoardFileByNo(int fileNo);
	
	/**
	 * 게시물 조회수 업데이트
	 * @param boardNo
	 */
	public void updateViewCnt(int boardNo);
	
	/**
	 * 게시물 좋아요 수 업데이트
	 * @param boardNo
	 */
	public void updateLikeCnt(int boardNo);
	
	/**
	 * 게시물 삭제
	 * @param boardNo
	 */
	public void deleteBoard(int boardNo);
	
	/**
	 * 게시물 수정
	 * @param board
	 */
	public void updateBoard(Board board);
	
}
