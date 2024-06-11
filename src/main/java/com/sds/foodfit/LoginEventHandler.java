package com.sds.foodfit;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

//스프링 시큐리티가 로그인 처리하는 시점을 알려주는 이벤트 메서드를 통해 개발자가 원하는 로근 처리로 커스텀 할 수 있다
//session member 를 담고 싶다..왜 통상적인 다른 로그인 기술 들과 통합하기 위해..(OAuth는 - anonymousUser 모름..)
@Slf4j
public class LoginEventHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler{                                       
	
	
	//유저가 스프링 시큐리티 기반으로 로그인을 성공하는 시점...
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		//현재 로그인 한 사용자로부터 member를 추출... 
		//로그인 성공한 스프링 시큐리티 유저는 CustomUserDetails 라는 객체를 인스턴스로 보유한 상태이므로, 
		//이 안에 들어잇는 Member DTO 를 꺼내서 session에 담자
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		Member member = userDetails.getMember();
		
		//세션에 member 넣기 
		HttpSession session = request.getSession();
		session.setAttribute("member", member);
		
		log.debug("로그인 결과 회원 정보는 " +member);
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
};