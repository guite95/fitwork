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
	int club_no;
	String leader;
	String club_name;
	String category;
	String location;
	String description;
	String head_count;
}
