package com.sds.foodfit.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Notice {

	private int noticeIdx;

	@NotBlank(message = "제목을 입력해주세요.")
	@Size(min = 5, max = 100, message = "제목은 5글자에서 100글자 사이로 입력해주세요.")
	private String title; // 제목

	private String writer; // 작성자

	@NotBlank(message = "내용을 입력해주세요.")
	private String content; // 내용

	private String regdate; // 날짜
	private int hit; // 조회수
}