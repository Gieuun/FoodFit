package com.sds.foodfit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter(AuthenticationManager authenticationManager) {
	super.setAuthenticationManager(authenticationManager);
	this.setFilterProcessesUrl("/recomember/login"); // 로그인 요청 URL
    }

    // 사용자가 로그인할때
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	    throws AuthenticationException {
	log.debug("로그인 시도중");

	// 사용자가 로그인폼에서 전송한 아이디, 패스워드에 이 메서드로 전달되는지부터 체크
	String id = request.getParameter("id");
	String password = request.getParameter("pwd");

	log.debug("===== 로그인필터 : 로그인 아이디 확인 " + id);
	log.debug("===== 로그인필터 : 로그인 pwd 확인 " + password);

	// 사용자에게 부여할 권한을 설정
	List<GrantedAuthority> authorities = new ArrayList<>();
	// Authentication 객체를 생성합니다.
	authorities.add(new SimpleGrantedAuthority("USER"));
	// 권한을 "ROLE_USER"로 설정
	Authentication auth = new UsernamePasswordAuthenticationToken(id, password, authorities);

	log.debug("auth 에 들어있느 객체는 " + auth);

	// 인증 매니저를 통해 인증 시도
	return this.getAuthenticationManager().authenticate(auth);

    }

    // 사용자가 로그인 성공할때
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	    Authentication authentication) throws IOException, ServletException {

	log.debug("LoginFilter에서 로그인 인증 성공");

	// 사용자의 세부 정보를 UserDetails 객체로 가져오기
	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	Member member = userDetails.getMember();

	for (GrantedAuthority auth : userDetails.getAuthorities()) {
	    log.debug("인증 성공 시 권한 정보는 " + auth.getAuthority());
	}

	// SecurityContextHolder에 Authentication 객체를 설정
	SecurityContextHolder.getContext().setAuthentication(authentication); // 이 코드가 존재해야, 권한이 스프링시큐리티에 저장됨

	// 세션에 사용자 세부 정보 저장
	HttpSession session = request.getSession(true);
	session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 시큐리티에 안걸리게 콘텍스트를 담아간다
	session.setAttribute("member", member);

	log.debug("세션에 사용자 정보 저장: 사용자명 = " + userDetails.getUsername());
	log.debug("memberIdx is" + member.getMemberIdx());

	// super.successfulAuthentication(request, response, chain, authentication);
	// chain.doFilter(request, response);

	chain.doFilter(request, response);
    }

    // 사용자가 로그인 실패할때
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException failed) throws IOException, ServletException {
	log.debug("로그인 실패: " + failed.getMessage());

	// 클라이언트에게 오류 메시지를 전달
	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 상태 설정
	response.getWriter().print("{ \"msg\": \"" + failed.getMessage() + "\" }");
	response.getWriter().flush();
    }
}