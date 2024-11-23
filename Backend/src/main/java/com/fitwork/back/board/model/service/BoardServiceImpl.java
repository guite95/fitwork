package com.fitwork.back.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitwork.back.board.model.dto.Board;
import com.fitwork.back.board.model.dto.BoardFile;
import com.fitwork.back.board.model.dto.BoardSearch;
import com.fitwork.back.board.model.dto.Comment;
import com.fitwork.back.board.model.repository.BoardRepository;

@Service
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
			boardFile.setBoardNo(board.getBoardNo());
			
			boardRepository.insertFile(boardFile);
		}
	}

	@Override
	public List<Board> list(BoardSearch boardSearch) {
		
		return boardRepository.selectAllBoard(boardSearch);
	}

	@Override
	public Board boardDetail(int boardNo) {
		boardRepository.updateViewCnt(boardNo);
		
		Board board = boardRepository.selectBoardByNo(boardNo);
		
		BoardFile boardFile = boardRepository.selectBoardFileByNo(boardNo);
		
		if (boardFile != null) {
			board.setBoardFile(boardFile);
		}
		
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
		board.setModified(true);
		board.getBoardFile().setBoardNo(board.getBoardNo());
		boardRepository.insertFile(board.getBoardFile());
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

	////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void addComment(Comment comment) {
		boardRepository.insertComment(comment);
	}

	@Override
	public List<Comment> commentList(int boardNo) {
		return boardRepository.selectCommentByBoardNo(boardNo);
	}

	@Override
	public List<Comment> userComment(String writer) {
		return boardRepository.selectCommentByWriter(writer);
	}

	@Override
	public void modifyComment(Comment comment) {
		boardRepository.updateComment(comment);
	}

	@Override
	public void deleteComment(int commentNo) {
		boardRepository.deleteComment(commentNo);
	}

}
