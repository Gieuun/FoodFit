package com.sds.foodfit;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sds.foodfit.domain.CustomUserDetails;

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
	String pwd = request.getParameter("pwd");

	log.debug("===== 로그인필터 : 로그인 아이디 확인 " + id);
	log.debug("===== 로그인필터 : 로그인 pwd 확인 " + pwd);

	// 이 객체를 생성해 인증매니저를통해 인증시도한다.
	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id, pwd);

	// 인증 매니저를 통해 인증 시도
	return this.getAuthenticationManager().authenticate(authToken);

    }

    // 사용자가 로그인 성공할때
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	    Authentication authentication) throws IOException, ServletException {
	log.debug("LoginFilte에서 로그인 인증 성공");

	// 사용자의 세부 정보를 UserDetails 객체로 가져오기
	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

	// 세션에 사용자 세부 정보 저장
	request.getSession().setAttribute("userDetails", userDetails);

	log.debug("세션에 사용자 정보 저장: 사용자명 = " + userDetails);

	HttpSession session = request.getSession();
	session.setAttribute("member", userDetails.getMember());
	log.debug("member is :" + userDetails.getMember());

	// FavoriteFood 확인
	if (userDetails.getFavoriteFood() == null) {
	    log.warn("사용자의 FavoriteFood가 null입니다.");
	    // null인 경우에도 로그인 절차를 계속 진행
	}

	chain.doFilter(request, response);
    }

    // 사용자가 로그인 실패할때
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException failed) throws IOException, ServletException {
	log.debug("로그인 실패: " + failed.getMessage());
    }
}
