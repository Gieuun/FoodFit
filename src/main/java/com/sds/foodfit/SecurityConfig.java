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
    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	http.authorizeHttpRequests((authorize) -> authorize

		.requestMatchers("/site/**").permitAll() // 모든 요청을 인증 없이 허용.
		.requestMatchers("/img/**").permitAll() // 모든 요청을 인증 없이 허용.
		.requestMatchers("/").permitAll() // 모든 요청을 인증 없이 허용.
		.requestMatchers("/recomember/login", "/recomember/loginform").permitAll()
		.requestMatchers("/recomember/regist", "/recomember/registform").permitAll()
		.requestMatchers("/notice/**").permitAll().requestMatchers("/rest/notice/**").permitAll()
		.requestMatchers("/recomember/sns/**").permitAll() // sns 이용자 요청 허용
		.requestMatchers("/rest/recomember/**").permitAll() // rest요청
		.requestMatchers("/recomember/mypage").hasAnyAuthority("USER") // 마이페이지
		.requestMatchers("/recofood/**").hasAnyAuthority("USER") // 회원만 이용
		.requestMatchers("/recomember/mypage").hasAnyAuthority("USER") // 회원만 이용

		.anyRequest().authenticated() // 그 외의 요청은 인증 필요
	);

	http.csrf((auth) -> auth.disable());

	/*
	 * ================================== UsernamePasswordAuthenticationFilter 재정의
	 * LoginFilte 클래스를 정의하면 기존의 폼 로그인은 비활성화 시켜야한다. LoginFilter 클래스가 로그인 요청을 처리하 하므로,
	 * setFilterProcessesUrl("member/login"); 를 등록해야한다.
	 * ===================================
	 */

	http.formLogin(login -> login.disable());

	http.logout(logout -> logout.logoutUrl("/logout") // 로그아웃요청 URL
		.logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
		.invalidateHttpSession(true) // HTTP 세션을 무효화할지 여부
		.deleteCookies("JSESSIONID") // 로그아웃 시 삭제할 쿠키 이름 설정
	);

	http.httpBasic(httpBasic -> httpBasic.realmName("FoodFit") // 기본 인증 사용 시 realm 이름 설정
	);

	http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
	// http.addFilterAfter(new SecurityContextPersistenceFilter(),
	// LoginFilter.class);

	return http.build();
    }

}