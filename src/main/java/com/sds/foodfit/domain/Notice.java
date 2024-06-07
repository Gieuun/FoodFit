package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Notice {
  
	private int noticeIdx;
	private String title; //제목
	private String writer; //작성자
	private String content; //내용
	private String regdate; //날짜
	private int hit; //조회수
}
