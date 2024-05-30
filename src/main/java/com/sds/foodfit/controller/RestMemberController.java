package com.sds.foodfit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.sns.KaKaoLogin;
import com.sds.foodfit.sns.NaverLogin;

@RestController
public class RestMemberController {

	private KaKaoLogin kakaoLogin;
	private NaverLogin naverLogin;

	@GetMapping("/rest/recomember/authform/{sns}")
	public ResponseEntity getLink(@PathVariable("sns") String sns) {
		ResponseEntity entity = null;

		if (sns.equals("naver")) {
			entity = ResponseEntity.ok(naverLogin.getGrantUrl());
		} else if (sns.equals("kakao")) {
			entity = ResponseEntity.ok(kakaoLogin.getGrantUrl());
		} else if (sns.equals("google")) {
			// entity=ResponseEntity.ok(googleLogin.getGrantUrl());
		}

		return entity;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("rest/recomember/adminlink")
	public ResponseEntity getAdminLink() {
		return ResponseEntity.ok("/admin/upload");
	}

}
