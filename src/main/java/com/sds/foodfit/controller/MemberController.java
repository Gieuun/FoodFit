package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.model.member.MemberService;
import com.sds.foodfit.model.member.RoleService;
import com.sds.foodfit.model.member.SnsService;
import com.sds.foodfit.sns.KaKaoLogin;
import com.sds.foodfit.sns.NaverLogin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private NaverLogin naverLogin;

	@Autowired
	private KaKaoLogin kakaoLogin;

	@Autowired
	private MemberService memberService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SnsService snsService;

	// 로그인 폼 요청 처리
		@GetMapping("/recomember/loginform")
		public String getLoginForm() {
			return "recomember/login";
		}

		// 로그인 요청처리
		@PostMapping("/recomember/login")
		public String login(Member member) {		
			
		memberService.selectByid(member.getId());
		//메서드 추가 작성	
		
			return "redirect:/";
		}
		
		// 로그아웃 요청 처리
		@GetMapping("/recomember/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			
			return "redirect:/";
		}
		
		
		// 회원가입 폼 요청 처리
		@GetMapping("/recomember/joinform")
		public String getJoinForm() {
			return "recomember/join";
		}

		// 건강정보 폼 요청 처리
		@GetMapping("/recomember/healthform")
		public String getHealthForm() {
			return "recomember/health";
		}

		// 마이페이지 폼 요청 처리
		@GetMapping("/recomember/mypageform")
		public String getMypageForm() {
			return "recomember/mypage";
		}

		// 홈페이지 회원가입 요청 처리
		@PostMapping("/recomember/regist")
		public String HealthForm(MemberDetail memberDetail, HttpSession session) {

			log.debug("회원가입 요청 시도");

			// 세션에서 임시 회원 정보 가져오기
			Member member = (Member) session.getAttribute("member");
			log.debug("member id"+member.getId());
			log.debug("member id"+member.getEmail());
			log.debug("member id"+member.getName());
			log.debug("member id"+member.getSns().getSnsName());
			
			log.debug("세션으로부터 꺼낸 member is "+member);
			
			// 일반 유저가 홈페이지 가입 시엔 USER 권한을 부여
			Role role = new Role();
			role.setRoleName("USER");
			member.setRole(role);

			// MemberDetail 객체 생성
			memberDetail.setMember(member); // 회원 정보 객체 설정

			// 데이터베이스에 회원 정보 저장하는 로직 수행
			log.debug("등록 컨트롤러 호출");
			memberService.regist(memberDetail);

			return null;
		}

}
