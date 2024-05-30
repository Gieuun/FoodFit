package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.MemberException;
import com.sds.foodfit.model.member.MemberDetailService;
import com.sds.foodfit.model.member.MemberService;
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
	private MemberService memberService;

	// 회원정보임시저장
	@PostMapping("/recomember/temp")
	public ResponseEntity temp(Member member, HttpSession session) {

		// 세션에 임시 회원 정보 담기
		session.setAttribute("member", member);

		System.out.println("name is" + member.getName());
		System.out.println("id is" + member.getId());
		System.out.println("password is" + member.getPwd());
		System.out.println("email is" + member.getEmail());

		ResponseEntity entity = ResponseEntity.status(HttpStatus.OK).build();
		return entity;
	}

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

	@ExceptionHandler(MemberException.class)
	public ResponseEntity handle(MemberException e) {
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;

	}

}
