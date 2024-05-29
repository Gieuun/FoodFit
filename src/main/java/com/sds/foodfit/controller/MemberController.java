package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.sns.KaKaoLogin;
import com.sds.foodfit.sns.NaverLogin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private NaverLogin naverLogin;
	
	@Autowired
	private KaKaoLogin kakaoLogin;
	
	//로그인 폼 요청 처리
	@GetMapping("/recomember/loginform")
	public String getLoginForm() {
		
		return "recomember/login";
	}
	
	//로그인 요청처리
	@PostMapping("/recomember/login")
	public String login(Member member) {
		return "redirect:/"; //로그인 성공 시 메인 재요청
	}
	
	//회원가입 폼 요청 처리
	@GetMapping("/recomember/joinform")
	public String getJoinForm() {
		return "recomember/join";
	}
	
	//건강정보 폼 요청 처리
	@GetMapping("/recomember/healthform")
	public String getHealthForm() {
		return "recomember/health";
	}
	
	//홈페이지 회원가입 요청처리
	@PostMapping("/recomember/health")
	public String Join(Member member) {
		
		log.debug("member id "+member.getId());
		log.debug("member id "+member.getEmail());
		log.debug("member id "+member.getName());
		log.debug("member id "+member.getSns().getSnsName());
		
		return null;
	}
	
}
