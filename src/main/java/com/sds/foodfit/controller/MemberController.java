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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.exception.MemberException;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private HttpSession session;
	
	// 로그인 폼 요청 처리
	@GetMapping("/recomember/loginform")
	public String getLoginForm() {
		return "recomember/login";
	}
	
	// 로그인 요청처리
	@PostMapping("/recomember/login")
	public ResponseEntity login(Member member) {
		log.debug("컨트롤러에서 로그인 요청 받음");
		return ResponseEntity.ok().build();
	}
		
	// 로그아웃 요청 처리
	@GetMapping("/recomember/logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:/";
	}

	// 회원가입 폼 요청 처리
	@GetMapping("/recomember/registform")
	public String getJoinForm() {
		return "recomember/regist";
	}
	
	 // 아이디 중복 확인 처리
	@PostMapping("/recomember/checkId")
	@ResponseBody
	public String checkId(@RequestParam("id") String id) {
	    boolean isExists = memberService.isIdExists(id);
	    return String.valueOf(isExists); // true 또는 false 문자열 반환
	}

     /*---------------------------------------------------------------------------------------------------------------------------
  	  아이디 변경 요청 처리
 	 --------------------------------------------------------------------------------------------------------------------------- */
	 @PostMapping("/recomember/updateId")
	 @ResponseBody
	 public String updateId(@RequestParam("newId") String newId, HttpSession session) {
	     Member loginUser = (Member) session.getAttribute("member");

	     // 세션에 저장된 회원 정보가 없다면 로그인 페이지로 리다이렉트
	     if (loginUser == null) {
	         return "redirect:/login";
	     }

	     // 새로운 아이디 유효성 검사를 수행 (필요에 따라 추가 구현)
	     if (!isValidId(newId)) {
	         return "invalid"; // 유효하지 않은 아이디일 경우 처리 방법 정의
	     }

	     try {
	         // 새로운 아이디로 회원 정보 업데이트
	         memberService.updateId(loginUser.getMemberIdx(), newId); // Service에서 아이디 업데이트 처리

	         // 업데이트된 회원 정보 세션에 다시 설정
	         loginUser.setId(newId);
	         session.setAttribute("member", loginUser);

	         return "success"; // 아이디 업데이트 성공
	     } catch (MemberException e) {
	         // 아이디 업데이트 중 예외 발생 시 처리
	         return "error"; // 실패 시 처리 방법 정의
	     }
	 }

	 // 아이디 유효성 검사 메서드
	 private boolean isValidId(String id) {
	     return true; // 임시로 true를 반환하도록 설정, 실제 구현 필요
	 }

	/*---------------------------------------------------------------------------------------------------------------------------
	  회원 정보 수정 처리
	 -------------------------------------------------------------------------------------------------------------------------- */
	
	@PostMapping("/recomember/update")
	public ResponseEntity<String> update(Member member, HttpSession session) {
	    Member loginUser = (Member) session.getAttribute("member");
	    log.debug("loginUser 정보는 " + loginUser);

	    member.setMemberIdx(loginUser.getMemberIdx());
	    memberService.update(member);
	    
	    //세션 정보 업데이트
	    session.setAttribute("member", member);

	    log.debug("회원 정보가 성공적으로 업데이트 되었습니다.");
	    return ResponseEntity.ok("회원 정보가 성공적으로 업데이트 되었습니다.");
    }
	
	/*---------------------------------------------------------------------------------------------------------------------------
	  비밀번호 변경 처리
	-------------------------------------------------------------------------------------------------------------------------- */
	@PostMapping("/recomember/changePassword")
	public ResponseEntity<String> changePassword(@RequestParam("currentPwd") String currentPwd, @RequestParam("newPwd") String newPwd, HttpSession session) {
		
		log.debug("비밀번호 변경 요청");
	    
	    Member loginUser = (Member) session.getAttribute("member");
	    log.debug("로그인된 사용자 ID 는 {}", loginUser.getId());
	    
	    // 현재 비밀번호 확인
	    if (!passwordEncoder.matches(currentPwd, loginUser.getPwd())) {
	        log.warn("현재 비밀번호가 일치하지 않습니다.");
	        return ResponseEntity.badRequest().body("currentPwdMismatch");
	    }
	    
	    log.debug("현재 비밀번호가 일치합니다.");
	    
	    // 새로운 비밀번호로 업데이트
	    String encodedNewPwd = passwordEncoder.encode(newPwd);
	    log.debug("새로운 비밀번호는(해시) ? {}", encodedNewPwd);
	    loginUser.setPwd(encodedNewPwd);
	    
	    try {
	        log.debug("비밀번호 업데이트 시도");
	        memberService.updatePassword(loginUser.getMemberIdx(), currentPwd, encodedNewPwd);
	        log.debug("비밀번호 업데이트 성공");
	    } catch (Exception e) {
	        log.error("비밀번호 업데이트 중 오류 발생", e);
	        return ResponseEntity.badRequest().body("error");
	    }

	    // 세션 정보 업데이트 (옵션)
	    session.setAttribute("member", loginUser);
	    log.debug("세션 정보 업데이트 완료");
	    
	    session.invalidate();
	    log.debug("세션 무효화 완료");
	    
	    return ResponseEntity.ok("success");
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------
	  이메일 중복 처리
	-------------------------------------------------------------------------------------------------------------------------- */
	 @PostMapping("/recomember/checkEmail")
	 public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> request) {
	        String email = request.get("email");
	        boolean isExists = memberService.isEmailExists(email);
	        return ResponseEntity.ok(isExists);
	    }
	
	/*---------------------------------------------------------------------------------------------------------------------------
	  회원 탈퇴 처리
	----------------------------------------------------------------------------------------------------------------------------- */	

	@PostMapping("/recomember/delete")
	public ResponseEntity<String> deleteMember(Member member, HttpSession session){
		// 현재 로그인된 회원 정보 가져오기
        Member loginUser = (Member) session.getAttribute("member");
        log.debug("loginUser 정보는 " + loginUser);

        try {
            // 회원 탈퇴 서비스 호출
            memberService.deleteMember(loginUser.getMemberIdx());

            // 세션에서 로그인 정보 제거
            session.removeAttribute("member");

            return ResponseEntity.ok("success");
        } catch (MemberException e) {
            log.error("회원 탈퇴 중 오류 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body("회원 탈퇴 실패: " + e.getMessage());
        }
    }
	/*---------------------------------------------------------------------------------------------------------------------------
	  홈페이지 회원가입 처리
	----------------------------------------------------------------------------------------------------------------------------- */
	@PostMapping("/recomember/regist")
	public String HealthForm(Member member, HttpSession session) {
		log.debug("회원가입 요청 시도");
		
		log.debug("member name "+member.getName());
		log.debug("member id "+member.getId());
		log.debug("member pwd "+member.getPwd());
		log.debug("member email "+member.getEmail());
		log.debug("member gender "+member.getGender());
		log.debug("member age "+member.getAge());
		log.debug("member height "+member.getHeight());
		log.debug("member weight "+member.getWeight());
		
		//세션에 회원 정보 담기
		session.setAttribute("member", member);
		
		//비밀번호 암호화
		String encodedPass = passwordEncoder.encode(member.getPwd());
		member.setPwd(encodedPass);
		log.debug("암호화된 비밀번호는 "+encodedPass);
				
		Member dto =(Member)session.getAttribute("member");
		log.debug("세션에서 꺼낸 member is ",dto);
		
		// 일반 유저가 홈페이지 가입 시엔 USER 권한을 부여
		Role role = new Role();
		role.setRoleName("USER");
		member.setRole(role);

		// 데이터베이스에 회원 정보 저장하는 로직 수행
		log.debug("등록 컨트롤러 호출");
		memberService.regist(member);
		
		// 세션 무효화하여 자동 로그인 방지
	    session.invalidate();
		
		return "redirect:/recomember/loginform";
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------
	  마이페이지 
	----------------------------------------------------------------------------------------------------------------------------- */	
	@GetMapping("/recomember/mypage")
	public String getMypageForm(Model model, HttpSession session) {
		
		// 로그인한 회원 정보 가져오기
		Member member = (Member) session.getAttribute("member");

		model.addAttribute("member", member);

		log.debug("member is{} " + member);

		return "recomember/mypage";
	}
	

	

	/*================================================================== 
	  네이버 서버에서들어온 콜백 요청처리
	 ==================================================================*/

	@GetMapping("/recomember/sns/naver/callback")
	public ModelAndView naverCallback(HttpServletRequest request, HttpSession session) {

		String code = request.getParameter("code");
		log.debug("네이버 인증 코드: " + code);
		
		if (code == null || code.isEmpty()) {
	        // 네이버 로그인 취소 처리
	        log.debug("네이버 로그인이 취소되었습니다.");
	        // 취소 처리 후 로그인 페이지로 리다이렉트 또는 다른 처리를 수행할 수 있습니다.
	        
	        // 예시: 로그인 페이지로 리다이렉트
	        return new ModelAndView("redirect:/login");
	    }

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
		log.debug("userinfo_url is "+userinfo_url);

		// Get 방식을 적용한 헤더 구성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		HttpEntity entity2 = new HttpEntity(headers2); // http 머리와 몸 하나로 묶어주는 객체

		// 비동기 객체이용
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity2, String.class);

		String userBody = userEntity.getBody();
		log.debug("네이버에서 보낸 사용자 정보: "+ userBody);

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


		Member dto  = memberService.selectById(id);

		if (dto == null ) {
			member.setPwd("");
			memberService.regist(member);
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
		log.debug("code is "+code);

		/*-----------------------------------------------
		 2. 토큰 요청을 위한 헤더와 바디 구성 후 post 요청
		 -----------------------------------------------*/

		// 바디 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", code);
		params.add("client_id", kakaoLogin.getClient_id());
		params.add("redirect_uri", kakaoLogin.getRedirect_uri());
		params.add("grant_type", kakaoLogin.getGrant_type());
		
		log.debug("code"+ code);
		log.debug("client_id"+ kakaoLogin.getClient_id());
		log.debug("redirect_uri"+ kakaoLogin.getRedirect_uri());
		log.debug("grant_type"+ kakaoLogin.getGrant_type());


		// 헤더 구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		// 머리 몸 합치기
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

		// 엑세스 토큰 요청
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(kakaoLogin.getToken_request_url(), entity, String.class);
		log.debug("responseEntity" +responseEntity);

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
		log.debug("userInfo_url " +userInfo_url);

		// Get 방식 헤더 구성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

		// 요청 엔터티 생성
		HttpEntity entity2 = new HttpEntity(headers2);

		// 사용자 정보 비동기 객체 요청
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userEntity = restTemplate2.exchange(kakaoLogin.getUserinfo_url(), HttpMethod.GET, entity2, String.class);

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
		member.setId(id);  // ID 설정
		member.setEmail(email);
		member.setName(nickname);
		member.setSns(snsService.selectByName("kakao"));

		// 권한 정보 설정
		Role role = roleService.selectByName("USER");
		member.setRole(role);


		// 이메일로 사용자 조회
		Member dto = memberService.selectById(email);

		if (dto == null) {
		    // 신규 사용자 등록
			member.setPwd("");
		    memberService.regist(member);
		    dto = member;
		    log.debug("가입처리");
		} else {
		    // 기존 사용자 권한 업데이트
		    log.debug("권한설정");
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
	/*--------------------------------------------------------------------------
	 구글 소셜 로그인 구현
	 --------------------------------------------------------------------------- */
	@GetMapping("/recomember/sns/google/callback")
	public ModelAndView googleCallback(@RequestParam("code") String code, HttpSession session) {
		log.debug("Google이 보낸 임시 코드는 :"+ code);
		
		RestTemplate restTemplate = new RestTemplate();
		String tokenRequestUrl = googleLogin.getToken_request_url(); //토큰 요청 url 가죠오기
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		//body 구성
		MultiValueMap<String, String> params =new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", googleLogin.getClient_id());
		params.add("client_secret", googleLogin.getClient_secret());
		params.add("redirect_uri", googleLogin.getRedirect_uri());
		params.add("grant_type", googleLogin.getGrant_type());
		
		HttpEntity httpEntity = new HttpEntity(params, headers);
		
		log.debug("code is " +code);
		log.debug("client_id is " + googleLogin.getClient_id());
		log.debug("client_secret is " + googleLogin.getClient_secret());
		log.debug("redirect_uri is " + googleLogin.getRedirect_uri());
		log.debug("grant_type is " + googleLogin.getGrant_type());
		
		
		//토큰 요청 및 응답 처리
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, httpEntity, String.class);
			String responseBody =responseEntity.getBody();
			log.debug("토큰이 포함된 응답 정보는 : "+ responseBody);
			
			ObjectMapper objectMapper = new ObjectMapper();
			GoogleOAuthToken oAuthToken = objectMapper.readValue(responseBody, GoogleOAuthToken.class);
			log.debug("구글로 부터 인증 후 받은 토큰 : " + oAuthToken.getAccess_token());
			
			//취득한 토큰 이용해 회원정보 접근
			String Userinfo_url = googleLogin.getUserinfo_url(); //회원정보 요청 주소
			log.debug("Userinfo_url is :"+Userinfo_url);
			
			HttpHeaders headers2 = new HttpHeaders();
			headers2.setBearerAuth(oAuthToken.getAccess_token()); //Bearer 토큰 설정
			
			HttpEntity<String> entity = new HttpEntity<>(headers2);
			
			try {
	            ResponseEntity<String> userEntity = restTemplate.exchange(Userinfo_url, HttpMethod.GET, entity, String.class);
	            String userBody = userEntity.getBody();
	            log.debug("userBody is " + userBody);

	            ObjectMapper objectMapper2 = new ObjectMapper();
	            HashMap<String, String> userMap = objectMapper2.readValue(userBody, new TypeReference<HashMap<String, String>>() {});

	            // 사용자 정보 파싱 및 처리
	            String uid = userMap.get("sub");
	            String email = userMap.get("email");
	            String nickname = userMap.get("name");

	            log.debug("uid is: " + uid);
	            log.debug("email is: " + email);
	            log.debug("nickname is: " + nickname);
	            
	            if(uid == null || uid.trim().isEmpty()) {
	            	log.error("Google에서 받은 uid가 null이거나 비어있습니다");
	            	throw new MemberException("Google에서 받은 uid가 null이거나 비어 있습니다");
	            }
	            
	            Member member = new Member();
	        	member.setId(uid);
	        	member.setEmail(email);
	        	member.setName(nickname);
	        	member.setSns(snsService.selectByName("google"));
	        	
	        	// 권한 정보 설정
	    		Role role = roleService.selectByName("USER");
	    		member.setRole(role);


	    		// 이메일로 사용자 조회
	    		Member dto = memberService.selectById(email);

	    		if (dto == null) {
	    		    // 신규 사용자 등록
	    			member.setPwd(""); //비밀번호 필드를null로 설정
	    		    memberService.regist(member);
	    		    dto = member;
	    		    log.debug("가입처리");
	    		} else {
	    		    // 기존 사용자 권한 업데이트
	    		    dto.setRole(role);
	    		    log.debug("권한설정");
	    		}
	            
	            //세션 설정
	        	session.setAttribute("member", dto);
	        	log.debug("현재 가진 권한 : "+dto.getRole().getRoleName());
	        	log.debug("member sns 회원정보 : "+ member);

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