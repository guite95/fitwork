package com.fitwork.back.board.model.service;

import java.util.HashMap;
import java.util.Map;

import com.fitwork.back.board.model.dto.Board;
import com.fitwork.back.board.model.dto.BoardFile;
import com.fitwork.back.board.model.dto.BoardSearch;
import com.fitwork.back.board.model.repository.BoardRepository;
import com.fitwork.back.util.PageResult;

public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	public BoardServiceImpl(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@Override
	public void addBoard(Board board) {
		boardRepository.insertBoard(board);
		
		BoardFile boardFile = board.getBoardFile();
		if (boardFile != null) {
			boardFile.setFileNo(board.getBoardNo());
			
			boardRepository.insertFile(boardFile);
		}
	}

	@Override
	public Map<String, Object> list(BoardSearch boardSearch) {
		Map<String, Object> result = new HashMap<>();
		
		result.put("list", boardRepository.selectAllBoard(boardSearch));
		result.put("pr", new PageResult(boardSearch.getPage(), boardRepository.selectBoardCount(boardSearch), boardSearch.getListSize()));
		
		return result;
	}

	@Override
	public Board boardDetail(int boardNo) {
		boardRepository.updateViewCnt(boardNo);
		
		Board board = boardRepository.selectBoardByNo(boardNo);
		
		board.setBoardFile(boardRepository.selectBoardFileByNo(boardNo));
		
		return board;
	}

	@Override
	public void increseLikeCnt(String id, int boardNo) {
		boardRepository.increaseLikeCnt(boardNo);
		boardRepository.insertLike(id, boardNo);
	}

	@Override
	public void decreaseLikeCnt(String id, int boardNo) {
		boardRepository.decreaseLikeCnt(boardNo);
		boardRepository.deleteLike(id, boardNo);
	}

	@Override
	public void deleteBoard(int boardNo) {
		boardRepository.deleteBoard(boardNo);
	}

	@Override
	public void modifyBoard(Board board) {
		boardRepository.updateBoard(board);
	}

	@Override
	public Board selectOne(int boardNo) {
		Board board = boardRepository.selectBoardByNo(boardNo);
		
		board.setBoardFile(boardRepository.selectBoardFileByNo(boardNo));
		
		return board;
	}

	@Override
	public void deleteFile(int fileNo) {
		boardRepository.deleteBoardFile(fileNo);
	}

}
