package com.fitwork.back.board.model.dto;

public class BoardSearch {
	private int page; // 현재 페이지
	private int listSize; // 한 번에 보여줄 게시물의 개수
	
	public BoardSearch() {
		this(1, 10);
	}
	
	public BoardSearch(int page) {
		this(page, 10);
	}
	
	public BoardSearch(int page, int listSize) {
		this.page = page;
		this.listSize = listSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	
	public int getOffset() {
		return (page - 1) * listSize;
	}
}
