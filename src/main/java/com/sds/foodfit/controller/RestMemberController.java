package com.sds.foodfit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.MemberException;
import com.sds.foodfit.sns.GoogleLogin;
import com.sds.foodfit.sns.KaKaoLogin;
import com.sds.foodfit.sns.NaverLogin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
public class RestMemberController {

	@Autowired
	private KaKaoLogin kakaoLogin;

	@Autowired
	private NaverLogin naverLogin;
	
	@Autowired
	private GoogleLogin googleLogin;
	
	// 로그인 요청에 필요한 링크 주소 및 파라미터 생성 요청 처리
	@GetMapping("/rest/recomember/authform/{sns}")
	public ResponseEntity getLink(@PathVariable("sns") String sns) {

		ResponseEntity entity = null;

		if (sns.equals("google")) {
			entity=ResponseEntity.ok(googleLogin.getGrantUrl()); //내용을 보내야 하므로, body도 구성하자
		} else if (sns.equals("naver")) {
			entity = ResponseEntity.ok(naverLogin.getGrantUrl()); // 내용을 보내야 하므로, body도 구성하자
		} else if (sns.equals("kakao")) {
			entity = ResponseEntity.ok(kakaoLogin.getGrantUrl()); // 내용을 보내야 하므로, body도 구성하자
		}

		return entity;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/rest/recomember/adminlink")
	public ResponseEntity getAdminLink() {
		return ResponseEntity.ok("/upload");
	}

	@ExceptionHandler(MemberException.class)
	public ResponseEntity handle(MemberException e) {
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;

	}

}
