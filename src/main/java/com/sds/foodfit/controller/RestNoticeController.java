package com.sds.foodfit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.domain.Notice;
import com.sds.foodfit.model.notice.NoticeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestNoticeController {

	@Autowired
	private NoticeService noticeService;

	@PostMapping("/notice/regist")
	public ResponseEntity<?> regist(@Validated @RequestBody Notice notice, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			noticeService.insert(notice);
			return ResponseEntity.ok("게시글이 성공적으로 등록되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 오류가 발생하였습니다.");
		}
	}
}