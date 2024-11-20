package com.fitwork.back.clubs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Club {
	private int clubNo;
	private String leader;
	private String clubName;
	private String category;
	private String location;
	private String description;
	private String headCount;
	private ClubFile clubFile;
}
