package com.sds.foodfit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;

	// 단방향 암호화(해시) 객체 등록함
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 시큐리티 필터체인 객체 호출 (접근허가 관련 작업)
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
				
				.requestMatchers("/site/**").permitAll() // 모든 요청을 인증 없이 허용.																				
				.requestMatchers("/**").permitAll() // 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
				.requestMatchers("/").permitAll() // 모든 요청을 인증 없이 허용.
				.requestMatchers("/recofood/**").permitAll() // 음식추천경로를 인증 없이 허용. 결과물 나오면 수정필요!
				.requestMatchers("/food/**").permitAll() // 음식추천요청을 인증 없이 허용. 결과물 나오면 수정필요!
				.requestMatchers("/rest/notice/**").permitAll() // 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
				
				.requestMatchers("/rest/recomember/**").permitAll()
				.requestMatchers("/recomember/sns/**").permitAll() // sns 이용자 요청 허용
				.requestMatchers("/recomember/mypage", "/recomember/mypage2").hasAuthority("USER")
																										

				.anyRequest().authenticated() // 그 외의 요청은 인증 필요
				
		).formLogin(form -> form
				
				.loginPage("/recomember/loginform")
				.successHandler(loginEventHandler()) //로그인 성공시
				.failureHandler(failureHandler()) //로그인 실패시
				.loginProcessingUrl("/recomember/login")
				.usernameParameter("id")
				.passwordParameter("pwd")
																														
		).httpBasic(httpBasic -> httpBasic.realmName("FoodFit") // 기본 인증 사용 시 realm 이름 설정
		)
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
		);
				
		//아래 핸들러가 대신 동작함 
		// LoginFilter loginFilter = new LoginFilter(authenticationManager());
		// loginFilter.setFilterProcessesUrl("/recomember/login");
		// http.addFilterBefore(loginFilter,
		// UsernamePasswordAuthenticationFilter.class);

		http.csrf((auth) -> auth.disable());
		return http.build();
	}
	
	//로그인 성공시 동작할 핸들러
	public AuthenticationSuccessHandler loginEventHandler() {
		log.debug("로그인 핸들러 요청");
		return new LoginEventHandler();
	}
	
	//로그인 실패시 동작할 핸들러
	public AuthenticationFailureHandler failureHandler() {
			
		return new FaildEventHandler();
	}

}