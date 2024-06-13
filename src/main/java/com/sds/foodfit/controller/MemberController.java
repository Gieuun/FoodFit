package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.model.member.MemberService;
import com.sds.foodfit.model.role.RoleService;
import com.sds.foodfit.model.sns.SnsService;
import com.sds.foodfit.sns.KaKaoLogin;
import com.sds.foodfit.sns.NaverLogin;

import jakarta.servlet.http.HttpServletRequest;
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
	return "redirect:/";
    }

    // 로그아웃 요청 처리
    @GetMapping("/logout")
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
    @GetMapping("/mypage")
    public String getMypageForm(Model model, HttpSession session) {

	// 로그인한 회원 정보 가져오기
	Member member = (Member) session.getAttribute("member");

	model.addAttribute("member", member);

	log.debug("member is{} " + member);

	return "recomember/mypage";
    }

    // 마이페이지 폼 요청 처리
    @GetMapping("/mypage2")
    public String getMypage2Form(Model model, HttpSession session) {

	// 로그인한 회원 정보 가져오기
	Member member = (Member) session.getAttribute("member");

	model.addAttribute("member", member);

	log.debug("member is{} " + member);

	return "recomember/mypage2";
    }

    // 홈페이지 회원가입 요청 처리
    @PostMapping("/recomember/regist")
    public String HealthForm(MemberDetail memberDetail, HttpSession session) {

	log.debug("회원가입 요청 시도");

	// 세션에서 임시 회원 정보 가져오기
	Member member = (Member) session.getAttribute("member");
	log.debug("member id" + member.getId());
	log.debug("member id" + member.getEmail());
	log.debug("member id" + member.getName());
	log.debug("member id" + member.getSns().getSnsName());

	log.debug("세션으로부터 꺼낸 member is " + member);

	// 일반 유저가 홈페이지 가입 시엔 USER 권한을 부여
	Role role = new Role();
	role.setRoleName("USER");
	member.setRole(role);

	// MemberDetail 객체 생성
	memberDetail.setMember(member); // 회원 정보 객체 설정

	// 데이터베이스에 회원 정보 저장하는 로직 수행
	log.debug("등록 컨트롤러 호출");
	memberService.regist(memberDetail);

	return "recomember/login";
    }

    /*
     * ======================================================== 네이버 서버에서 들어온 콜백 요청
     * 처리 ========================================================
     */
    @GetMapping("/recomember/sns/naver/callback")
    public ModelAndView naverCallback(HttpServletRequest request, HttpSession session) {

	Member member = (Member) session.getAttribute("member");
	session.setAttribute("member", member);
	log.debug("member is {}", member);

	String code = request.getParameter("code");

	// 토큰 요청을 위한 Post 헤더 Body 구성
	String token_url = naverLogin.getToken_request_url();

	// 바디구성
	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	params.add("code", code);
	params.add("client_id", naverLogin.getClient_id());
	params.add("client_secret", naverLogin.getClient_secret());
	params.add("redirect_uri", naverLogin.getRedirect_uri());
	params.add("grant_Type", naverLogin.getGrant_type());
	params.add("state", naverLogin.getState());

	// post 헤더 구성
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Type", "application/x-www-form-urlencoded");

	// 머리 몸 합치기
	HttpEntity entity = new HttpEntity(params, headers);

	// 비동식 방식 post 요청
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> responseEntity = restTemplate.exchange(token_url, HttpMethod.POST, entity, String.class);

	// 응답 정보에 들어있는 데이터 중 토큰 꺼내기
	String body = responseEntity.getBody();
	log.debug("네이버가 보낸 인증 완료 정보는 " + body);

	return null;
    }

}