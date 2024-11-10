package com.fitwork.back.board.model.dto;

public class BoardSearch {
	private int page;
	private int listSize;
	private String key;
	private String word;
	private String orderBy;
	private String orderByDir;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByDir() {
		return orderByDir;
	}

	public void setOrderByDir(String orderByDir) {
		this.orderByDir = orderByDir;
	}
	
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

	public int getOffset() { 
		return (page - 1) * listSize;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	
	
}
