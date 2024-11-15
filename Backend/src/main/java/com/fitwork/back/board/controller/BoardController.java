package com.fitwork.back.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitwork.back.board.model.dto.Board;
import com.fitwork.back.board.model.dto.BoardFile;
import com.fitwork.back.board.model.dto.BoardSearch;
import com.fitwork.back.board.model.service.BoardService;

@RestController
@RequestMapping("/api-board")
public class BoardController {
  
	private final BoardService boardService;
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/**
	 * 전체 게시물 조회
	 * @param boardSearch
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<Object> list(BoardSearch boardSearch) {
		
		try {
			Map<String, Object> result = boardService.list(boardSearch);
			
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 에러가 발생했습니다.");
		}
		
	}
	
	/**
	 * 게시물 상세 조회
	 * @param boardNo
	 * @return
	 */
	@GetMapping("/{boardNo}")
	public ResponseEntity<Object> detail(@PathVariable int boardNo) {
		
		try {
			Board board = boardService.boardDetail(boardNo);
			
			if (board == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당하는 정보가 없습니다.");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 에러가 발생헀습니다.");
		}
		
	}
	
	/**
	 * 게시물 등록
	 * @param board
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping
	public ResponseEntity<String> write(@RequestPart Board board, @RequestPart MultipartFile file) throws IllegalStateException, IOException {
		
		try {
			String oriName = file.getOriginalFilename();
			
			if (oriName.length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH");
				String subDir = sdf.format(new Date());
				
				File dir = new File("c:/SSAFY/final-prj/board/img" + subDir); // 디렉토리 경로를 나타내는 파일 객체
				dir.mkdirs();
				
				String systemName = UUID.randomUUID().toString() + oriName;
				
				file.transferTo(new File(dir, systemName)); // 메모리의 파일 정보를 특정 위치에 저장
				
				BoardFile boardFile = new BoardFile();
				boardFile.setPath(subDir);
				boardFile.setOriName(oriName);
				boardFile.setSystemName(systemName);
				
				board.setBoardFile(boardFile);
			}
			boardService.addBoard(board);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("글이 등록되지 않았습니다. 잠시 후 다시 시도해주세요");
		}
		
	}
	
	/**
	 * 게시물 삭제
	 * @param boardNo
	 * @return
	 */
	@DeleteMapping("{boardNo}")
	public ResponseEntity<String> delete(@PathVariable int boardNo) {
		
		try {
			boardService.deleteBoard(boardNo);
			return ResponseEntity.status(HttpStatus.OK).body("파일이 정상적으로 삭제되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 에러가 발생했습니다.");
		}
		
	}
	
	/**
	 * 게시물 수정
	 * @param boardNo
	 * @param board
	 * @param file
	 * @return
	 */
	@PutMapping("/modify/{boardNo}")
	public ResponseEntity<String> modify(@PathVariable int boardNo, @RequestPart Board board, @RequestPart MultipartFile file) {
		try {
			
			String oriName = file.getOriginalFilename();
			
			if (oriName.length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/HH");
				String subDir = sdf.format(new Date());
				
				File dir = new File("c:/SSAFY/final-prj/board/img" + subDir); // 디렉토리 경로를 나타내는 파일 객체
				dir.mkdirs();
				
				String systemName = UUID.randomUUID().toString() + oriName;
				
				file.transferTo(new File(dir, systemName)); // 메모리의 파일 정보를 특정 위치에 저장
				
				BoardFile boardFile = new BoardFile();
				boardFile.setPath(subDir);
				boardFile.setOriName(oriName);
				boardFile.setSystemName(systemName);
				
				board.setBoardFile(boardFile);
			} else {
				board.setBoardFile(null);
			}
			
			boardService.modifyBoard(board);
			
			return ResponseEntity.status(HttpStatus.OK).body("수정완료");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		}
	}
	
	/**
	 * 좋아요 수 증가
	 * @param boardNo
	 * @param id
	 * @return
	 */
	@PutMapping("/plus/{boardNo}")
	public ResponseEntity<String> likePlus(@PathVariable int boardNo, @RequestBody String id) {
		try {
			boardService.increseLikeCnt(id, boardNo);
			return ResponseEntity.status(HttpStatus.OK).body("좋아요 증가");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		}
	}
	
	/**
	 * 좋아요 수 감소
	 * @param boardNo
	 * @param id
	 * @return
	 */
	@PutMapping("/minus/{boardNo}")
	public ResponseEntity<String> likeMinus(@PathVariable int boardNo, @RequestBody String id) {
		try {
			boardService.decreaseLikeCnt(id, boardNo);
			return ResponseEntity.status(HttpStatus.OK).body("좋아요 감소");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		}
	}
	
}
