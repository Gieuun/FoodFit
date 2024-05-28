package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Notice {
	private int notice_idx; //noticeIdx(관례)
	private String title;
	private String writer;
	private String content;
	private String regdate; //LocalDateTime  
	private int hit;
}
