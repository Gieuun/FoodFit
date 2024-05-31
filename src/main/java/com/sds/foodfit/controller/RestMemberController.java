package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
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
	
	@Autowired
	private MemberDetailService memberDetailService;
	
	

	// 회원정보임시저장
	@PostMapping("rest/recomember/temp")
	public ResponseEntity temp(Member member, HttpSession session) {

		log.debug("temp() called with member: {}", member);
		// 세션에 임시 회원 정보 담기
		session.setAttribute("member", member);
		log.debug("Session attibute 'member' set: {} ", session.getAttribute("member"));
		
		System.out.println("name is " + member.getName());
		System.out.println("id is " + member.getId());
		System.out.println("password is " + member.getPwd());
		System.out.println("email is " + member.getEmail());

		ResponseEntity entity = ResponseEntity.status(HttpStatus.OK).build();
		return entity;
	}
	
	//임시 저장한 첫번째 세션에 저장된 임시 회원 정보를 가져와서 추가정보와 합쳐서 회원가입처리하는 컨트롤러
	@PostMapping("rest/recomember/healthform")
	public ResponseEntity<String> healthJoin(MemberDetail memberDtail, HttpSession session) {
		
		log.debug("healthJoin() called with memberDetail: {}", memberDtail);
		//세션에 저장된 임시 회원 정보 가져오기
		Member member = (Member)session.getAttribute("member");
		
		//세션에 임시 회원 정보가 없는 경우 처리
		if(member == null) {
			log.debug("member session이 저장이 안됬다");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("임시 회원 정보가 없습니다.");
		}
		
		//임시회원 정보와 추가 정보 합치기
		member.setMemberDetail(memberDtail);
		
		//회원 가입 후 세션에서 임시 회원 정보 제거
		session.removeAttribute("member");
		log.debug("member after setting memberDetail: {}", member);
		
		System.out.println("gender is " + memberDtail.getGender());
		System.out.println("age is " + memberDtail.getAge());
		System.out.println("height is " + memberDtail.getHeight());
		System.out.println("weight is " + memberDtail.getWeight());
		
		//여기서실제 회원가입 로직 호출
		//memberService.regist(member);
		
		
				
		return ResponseEntity.ok("회원가입을 성공했습니다");
	}
	
	
	@GetMapping("rest/recomember/authform/{sns}")
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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("rest/recomember/adminlink")
	public ResponseEntity getAdminLink() {
		return ResponseEntity.ok("/admin/upload");
	}

}
