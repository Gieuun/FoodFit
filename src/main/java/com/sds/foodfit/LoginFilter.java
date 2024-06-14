package com.sds.foodfit;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter{

	//AuthenticationManager 는 인터페이스이고, 스프링시큐리티 내부적으로 생성되어 처리되므로, 
	//개발자는 스프링으로부터 얻어와 사용해야 한다..
	private AuthenticationManager authenticationManager;
	
	public LoginFilter(AuthenticationManager authenticationManager) throws Exception {
		this.authenticationManager = authenticationManager;
	}
	
	//사용자가 로그인할때
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		//사용자가 로그인폼에서 전송한 아이디, 패스워드에 이 메서드로 전달되는지부터 체크
		String username = request.getParameter("id");
		String password = request.getParameter("pwd");
		
		log.debug("username is ================="+username);
		log.debug("password is ================="+password);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		log.debug("authToken is "+authToken);
		
	
		//인증 매니저를 통해 인증 시도
		return authenticationManager.authenticate(authToken);
	
	}
	
	//사용자가 로그인 성공할때
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		log.debug("로그인 성공:" + authentication.getName());
		super.successfulAuthentication(request, response, chain, authentication);
	}
	
	//사용자가 로그인 실패할때
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		log.debug("로그인 실패: "+ failed.getMessage());
		super.unsuccessfulAuthentication(request, response, failed); 
	}
}
