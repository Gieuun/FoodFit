package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Notice {
	private int notice_idx; 
	private String title;
	private String writer;
	private String content;
	private String regdate; //LocalDateTime  
	private int hit;
}
