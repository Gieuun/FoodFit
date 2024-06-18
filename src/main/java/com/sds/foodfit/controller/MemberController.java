package com.sds.foodfit.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.model.member.MemberService;
import com.sds.foodfit.model.role.RoleService;
import com.sds.foodfit.model.sns.SnsService;
import com.sds.foodfit.sns.GoogleLogin;
import com.sds.foodfit.sns.GoogleOAuthToken;
import com.sds.foodfit.sns.KaKaoLogin;
import com.sds.foodfit.sns.KaKaoOAuthToken;
import com.sds.foodfit.sns.NaverLogin;
import com.sds.foodfit.sns.NaverOAuthToken;

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
    private GoogleLogin googleLogin;

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
    @GetMapping("/recomember/registform")
    public String getHealthForm() {
	return "recomember/regist";
    }

    // 마이페이지 폼 요청 처리
    @GetMapping("/recomember/mypage")
    public String getMypageForm(Model model, HttpSession session) {
	// 로그인한 회원 정보 가져오기
	Member member = (Member) session.getAttribute("member");

	model.addAttribute("member", member);

	log.debug("member is{} " + member);

	return "recomember/mypage";
    }

    // 마이페이지 폼 요청 처리
    @GetMapping("/recomember/mypage2")
    public String getMypage2Form(Model model, HttpSession session) {

	// 로그인한 회원 정보 가져오기
	Member member = (Member) session.getAttribute("member");

	model.addAttribute("member", member);

	log.debug("member is{} " + member);

	return "recomember/mypage2";
    }

    // 홈페이지 회원가입 요청 처리
    @PostMapping("/recomember/regist")
    public String HealthForm(Member member, HttpSession session) {

	log.debug("회원가입 요청 시도");

	// 세션에서 임시 회원 정보 가져오기
	member = (Member) session.getAttribute("temp");
	log.debug("member name " + member.getName());
	log.debug("member id " + member.getId());
	log.debug("member pwd " + member.getPwd());
	log.debug("member email " + member.getEmail());

	log.debug("세션으로부터 꺼낸 member is " + member);

	// 일반 유저가 홈페이지 가입 시엔 USER 권한을 부여
	Role role = new Role();
	role.setRoleName("USER");
	member.setRole(role);

	// 데이터베이스에 회원 정보 저장하는 로직 수행
	log.debug("등록 컨트롤러 호출");
	memberService.regist(member);

	// 임시 정보 회원가입ㅎ
	session.removeAttribute("temp");

	return null;
    }

    /*
     * ================================================================== 네이버
     * 서버에서들어온 콜백 요청처리
     * ==================================================================
     */

    @GetMapping("/recomember/sns/naver/callback")
    public ModelAndView naverCallback(HttpServletRequest request, HttpSession session) {

	String code = request.getParameter("code");
	log.debug("네이버 인증 코드: " + code);

	// 토큰 요청을 위한 Post 헤더 Body 구성
	String token_url = naverLogin.getToken_request_url();

	// 바디구성
	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	params.add("code", code);
	params.add("client_id", naverLogin.getClient_id());
	params.add("client_secret", naverLogin.getClient_secret());
	params.add("redirect_uri", naverLogin.getRedirect_uri());
	params.add("grant_type", naverLogin.getGrant_type());
	params.add("state", naverLogin.getState());

	// post 헤더 구성
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Type", "application/x-www-form-urlencoded");

	// 머리 몸 합치기
	HttpEntity entity = new HttpEntity(params, headers);

	// 비동식 방식 post 요청
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> responseEntity = restTemplate.exchange(token_url, HttpMethod.POST, entity, String.class);

	/*--------------------------------------------------------------------------
	  응답정보에 들어있는 데이터 중 토큰 꺼내기
	 --------------------------------------------------------------------------- */
	String body = responseEntity.getBody();
	log.debug("네이버가 보낸 인증 완료 정보는 " + body); // 로그 성공

	// String 에 불과한 자료에서 토큰을 접근하려면 JSON 파싱을 해야한다.
	ObjectMapper objectMapper = new ObjectMapper();

	NaverOAuthToken oAuthToken = null;

	try {
	    // json 파싱 후 자바 객체에 담는다
	    oAuthToken = objectMapper.readValue(body, NaverOAuthToken.class);
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	/*--------------------------------------------------------------------------
	 토큰 정보를 이용하여 네이버 회원 정보 가져오기
	 --------------------------------------------------------------------------- */
	String userinfo_url = naverLogin.getUserinfo_url();
	log.debug("userinfo_url is " + userinfo_url);

	// Get 방식을 적용한 헤더 구성
	HttpHeaders headers2 = new HttpHeaders();
	headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
	HttpEntity entity2 = new HttpEntity(headers2); // http 머리와 몸 하나로 묶어주는 객체

	// 비동기 객체이용
	RestTemplate restTemplate2 = new RestTemplate();
	ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity2, String.class);

	String userBody = userEntity.getBody();
	log.debug("네이버에서 보낸 사용자 정보: " + userBody);

	// 사용자 정보 추출하기
	ObjectMapper objectMapper2 = new ObjectMapper();

	// 준비된 DTO가 없을 걍우, HashMap 꺼내자
	HashMap<String, Object> userMap = null;

	try {
	    // 두번째 인수는 인스턴스가 아닌 동적 클래스로 HashMap.class가 와야함.
	    userMap = objectMapper2.readValue(userBody, HashMap.class);
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	Map<String, Object> response = (Map<String, Object>) userMap.get("response");
	String id = (String) response.get("id");
	String email = (String) response.get("email");
	String name = (String) response.get("name");

	log.debug("id = " + id);
	log.debug("email = " + email);
	log.debug("name = " + name);

	// 신규회원 가입자 경우, 회원 정보에 sns 유형, 권한정보 입력해야 하므로 Member DTO에 정보구성
	Member member = new Member();
	member.setId(id);
	member.setName(name);
	member.setEmail(email);
	member.setSns(snsService.selectByName("naver"));

	// 권한 정보 설정
	Role role = roleService.selectByName("USER"); // 일반 회원가입이므로
	member.setRole(role);

	Member dto = memberService.selectById(id);

	if (dto == null) {
	    MemberDetail memberDetail = new MemberDetail();
	    memberDetail.setMember(member);
	    memberService.regist(memberDetail);
	    dto = member;
	} else {
	    dto.setRole(role);
	}

	session.setAttribute("member", dto);
	log.debug("현재 가진 권한은 " + dto.getRole().getRoleName());
	log.debug("member sns 회원정보 " + member);

	Authentication auth = new UsernamePasswordAuthenticationToken(member.getName(), null,
		Collections.singletonList(new SimpleGrantedAuthority("USER")));
	SecurityContextHolder.getContext().setAuthentication(auth);
	session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

	// 로그인 성공후 메인페이지로
	ModelAndView mav = new ModelAndView("redirect:/");

	return mav;
    }

    /*-----------------------------------------------
     카카오 콜백요청 처리
     -----------------------------------------------*/
    @GetMapping("/recomember/sns/kakao/callback")
    public ModelAndView kakaoCallback(HttpServletRequest request, HttpSession session) {

	// 1. 콜백으로부터 전달된 인가 코드 받기
	String code = request.getParameter("code");
	log.debug("code is " + code);

	/*-----------------------------------------------
	 2. 토큰 요청을 위한 헤더와 바디 구성 후 post 요청
	 -----------------------------------------------*/

	// 바디 구성
	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	params.add("code", code);
	params.add("client_id", kakaoLogin.getClient_id());
	params.add("redirect_uri", kakaoLogin.getRedirect_uri());
	params.add("grant_type", kakaoLogin.getGrant_type());

	log.debug("code" + code);
	log.debug("client_id" + kakaoLogin.getClient_id());
	log.debug("redirect_uri" + kakaoLogin.getRedirect_uri());
	log.debug("grant_type" + kakaoLogin.getGrant_type());

	// 헤더 구성
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Type", "application/x-www-form-urlencoded");

	// 머리 몸 합치기
	HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

	// 엑세스 토큰 요청
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> responseEntity = restTemplate.postForEntity(kakaoLogin.getToken_request_url(), entity,
		String.class);
	log.debug("responseEntity" + responseEntity);

	// 응답 확인
	String body = responseEntity.getBody();
	log.debug("카카오에서 보낸 인증 응답 정보 " + body);

	ObjectMapper objectMapper = new ObjectMapper();
	KaKaoOAuthToken oAuthToken = null;

	try {
	    // String 을 자바객체로 반환
	    oAuthToken = objectMapper.readValue(body, KaKaoOAuthToken.class);
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	/*-----------------------------------------------
	 3. 토큰을 이용하여 사용자 정보 요청
	 -----------------------------------------------*/
	// 사용자 정보 여청 URL GET 방식
	String userInfo_url = kakaoLogin.getUserinfo_url();
	log.debug("userInfo_url " + userInfo_url);

	// Get 방식 헤더 구성
	HttpHeaders headers2 = new HttpHeaders();
	headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

	// 요청 엔터티 생성
	HttpEntity entity2 = new HttpEntity(headers2);

	// 사용자 정보 비동기 객체 요청
	RestTemplate restTemplate2 = new RestTemplate();
	ResponseEntity<String> userEntity = restTemplate2.exchange(kakaoLogin.getUserinfo_url(), HttpMethod.GET,
		entity2, String.class);

	String userBody = userEntity.getBody();
	log.debug("카카오 에서 보낸 사용자 정보 " + userBody);

	// JSON 형태의 사용자 정보를 자바 객체로 변환
	ObjectMapper objectMapper2 = new ObjectMapper();

	HashMap<String, Object> userMap = null;

	try {
	    userMap = objectMapper2.readValue(userBody, HashMap.class);
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	// JSON 데이터에서 id, nickname, email 추출
	String id = String.valueOf(userMap.get("id").toString()); // id를 Long으로 변환
	Map<String, Object> properties = (Map<String, Object>) userMap.get("properties");
	String nickname = (String) properties.get("nickname");
	Map<String, Object> kakaoAccount = (Map<String, Object>) userMap.get("kakao_account");
	String email = (String) kakaoAccount.get("email");

	// 로그 출력
	log.debug("ID is " + id);
	log.debug("nickname is " + nickname);
	log.debug("email is " + email);

	// Member 객체 설정
	Member member = new Member();
	member.setId(id); // ID 설정
	member.setEmail(email);
	member.setName(nickname);
	member.setSns(snsService.selectByName("kakao"));

	// 권한 정보 설정
	Role role = roleService.selectByName("USER");
	member.setRole(role);

	MemberDetail memberDetail = new MemberDetail();
	memberDetail.setMember(member);

	// 이메일로 사용자 조회
	Member dto = memberService.selectById(email);

	if (dto == null) {
	    // 신규 사용자 등록
	    memberService.regist(memberDetail);
	    dto = member;
	    log.debug("가입처리");
	} else {
	    // 기존 사용자 권한 업데이트
	    log.debug("권한설정");
	    dto.setRole(role);
	}

	session.setAttribute("member", dto);
	log.debug("현재 가진 권한은 " + dto.getRole().getRoleName());
	log.debug("memberDetail 회원정보 " + memberDetail);
	log.debug("member sns 회원정보 " + member);

	Authentication auth = new UsernamePasswordAuthenticationToken(member.getName(), null,
		Collections.singletonList(new SimpleGrantedAuthority("USER")));
	SecurityContextHolder.getContext().setAuthentication(auth);
	session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

	// 로그인 성공후 메인페이지로
	ModelAndView mav = new ModelAndView("redirect:/");

	return mav;
    }

    /*--------------------------------------------------------------------------
     구글 소셜 로그인 구현 구현이 잘안됨,,
     --------------------------------------------------------------------------- */
    @GetMapping("/recomember/sns/google/callback")
    public ModelAndView googleCallback(@RequestParam("code") String code, HttpSession session) {
	log.debug("Google이 보낸 임시 코드는 :" + code);

	RestTemplate restTemplate = new RestTemplate();
	String tokenRequestUrl = googleLogin.getToken_request_url(); // 토큰 요청 url 가죠오기

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	// body 구성
	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	params.add("code", code);
	params.add("client_id", googleLogin.getClient_id());
	params.add("client_secret", googleLogin.getClient_secret());
	params.add("redirect_uri", googleLogin.getRedirect_uri());
	params.add("grant_type", googleLogin.getGrant_type());

	HttpEntity httpEntity = new HttpEntity(params, headers);

	log.debug("code is " + code);
	log.debug("client_id is " + googleLogin.getClient_id());
	log.debug("client_secret is " + googleLogin.getClient_secret());
	log.debug("redirect_uri is " + googleLogin.getRedirect_uri());
	log.debug("grant_type is " + googleLogin.getGrant_type());

	// 토큰 요청 및 응답 처리
	try {
	    ResponseEntity<String> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, httpEntity,
		    String.class);
	    String responseBody = responseEntity.getBody();
	    log.debug("토큰이 포함된 응답 정보는 : " + responseBody);

	    ObjectMapper objectMapper = new ObjectMapper();
	    GoogleOAuthToken oAuthToken = objectMapper.readValue(responseBody, GoogleOAuthToken.class);
	    log.debug("구글로 부터 인증 후 받은 토큰 : " + oAuthToken.getAccess_token());

	    // 취득한 토큰 이용해 회원정보 접근
	    String Userinfo_url = googleLogin.getUserinfo_url(); // 회원정보 요청 주소
	    log.debug("Userinfo_url is :" + Userinfo_url);

	    HttpHeaders headers2 = new HttpHeaders();
	    headers2.setBearerAuth(oAuthToken.getAccess_token()); // Bearer 토큰 설정

	    HttpEntity<String> entity = new HttpEntity<>(headers2);

	    try {
		ResponseEntity<String> userEntity = restTemplate.exchange(Userinfo_url, HttpMethod.GET, entity,
			String.class);
		String userBody = userEntity.getBody();
		log.debug("userBody is " + userBody);

		ObjectMapper objectMapper2 = new ObjectMapper();
		HashMap<String, String> userMap = objectMapper2.readValue(userBody,
			new TypeReference<HashMap<String, String>>() {
			});

		// 사용자 정보 파싱 및 처리
		String uid = userMap.get("id");
		String email = userMap.get("email");
		String nickname = userMap.get("name");

		log.debug("uid is: " + uid);
		log.debug("email is: " + email);
		log.debug("nickname is: " + nickname);

		Member member = new Member();
		member.setId(uid);
		member.setEmail(email);
		member.setName(nickname);
		member.setSns(snsService.selectByName("google"));

		// 권한 정보 설정
		Role role = roleService.selectByName("USER");
		member.setRole(role);

		MemberDetail memberDetail = new MemberDetail();
		memberDetail.setMember(member);

		// 이메일로 사용자 조회
		Member dto = memberService.selectById(email);

		if (dto == null) {
		    // 신규 사용자 등록
		    memberService.regist(memberDetail);
		    dto = member;
		    log.debug("가입처리");
		} else {
		    // 기존 사용자 권한 업데이트
		    dto.setRole(role);
		    log.debug("권한설정");
		}

		// 세션 설정
		session.setAttribute("member", dto);
		log.debug("현재 가진 권한 : " + dto.getRole().getRoleName());
		log.debug("memberDetail 회원 정보 : " + memberDetail);
		log.debug("member sns 회원정보 : " + member);

		Authentication auth = new UsernamePasswordAuthenticationToken(member.getName(), null,
			Collections.singletonList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

	    } catch (RestClientException | IOException e) {
		e.printStackTrace();
		// 예외 처리 필요
	    }
	} catch (RestClientException | IOException e) {
	    e.printStackTrace();
	    // 예외 처리 필요
	}

	// 로그인 성공후 메인페이지로
	ModelAndView mav = new ModelAndView("redirect:/");

	return mav;
    }

}