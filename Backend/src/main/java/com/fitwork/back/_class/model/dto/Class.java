package com.fitwork.back._class.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Class {
	private int classNo;
	private String leader;
	private String className;
	private String category;
	private String location;
	private String description;
	private int headCount;
	private int price;
}
