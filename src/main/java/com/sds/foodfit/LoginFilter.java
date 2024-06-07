package com.sds.foodfit;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter{

	
	private AuthenticationManager authenticationManager;
	
	public LoginFilter(AuthenticationManager authenticationManager) throws Exception {
		this.authenticationManager = authenticationManager;
	}
	
	//사용자가 로그인할때
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		//사용자가 로그인폼에서 전송한 아이디, 패스워드에 이 메서드로 전달되는지부터 체크
		String username = this.obtainUsername(request);
		String password = this.obtainPassword(request);
		
		log.debug("username is ================="+username);
		log.debug("password is ================="+password);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password, null);
		
		return authenticationManager.authenticate(auth);
	}

}
