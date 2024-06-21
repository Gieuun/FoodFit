package com.sds.foodfit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public LoginFilter loginFilter() throws Exception {
	AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
	LoginFilter loginFilter = new LoginFilter(authenticationManager);
	return loginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    // 시큐리티 필터체인 객체 호출 (접근허가 관련 작업)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	http.authorizeHttpRequests((authorize) -> authorize

		.requestMatchers("/site/**").permitAll() // 모든 요청을 인증 없이 허용.
		.requestMatchers("/**").permitAll() // 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
		.requestMatchers("/").permitAll() // 모든 요청을 인증 없이 허용.
		.requestMatchers("/recofood/**").permitAll() // 음식추천경로를 인증 없이 허용. 결과물 나오면 수정필요!
		.requestMatchers("/food/**").permitAll() // 음식추천요청을 인증 없이 허용. 결과물 나오면 수정필요!
		.requestMatchers("/favoritefood/**").permitAll() // 즐겨찾기 호출 허용
		.requestMatchers("/rest/favoritefood/**").permitAll() // 즐겨찾기 rest 호출 허용

		.requestMatchers("/rest/notice/**").permitAll() // 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
		.requestMatchers("/recomember/sns/**").permitAll() // sns 이용자 요청 허용
		.requestMatchers("/rest/recomember/**").permitAll()

		.requestMatchers("/recomember/mypage", "/recomember/favorite").hasAuthority("USER")
		.requestMatchers("/headerinfo").hasAuthority("USER") // 헤드 값 달고 다니기

		.anyRequest().authenticated() // 그 외의 요청은 인증 필요
	);

	http.csrf((auth) -> auth.disable());

	/*
	 * ================================== UsernamePasswordAuthenticationFilter 재정의
	 * LoginFilte 클래스를 정의하면 기존의 폼 로그인은 비활성화 시켜야한다. LoginFilter 클래스가 로그인 요청을 처리하 하므로,
	 * setFilterProcessesUrl("member/login"); 를 등록해야한다.
	 * ===================================
	 */
	http.formLogin(login -> login.disable())

		.logout(logout -> logout.logoutUrl("/logout") // 로그아웃요청 URL
			.logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
			.invalidateHttpSession(true) // HTTP 세션을 무효화할지 여부
			.deleteCookies("JSESSIONID") // 로그아웃 시 삭제할 쿠키 이름 설정
		);

	http.httpBasic(httpBasic -> httpBasic.realmName("FoodFit") // 기본 인증 사용 시 realm 이름 설정
	);

	http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

	return http.build();
    }

}