package com.sds.foodfit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
public class SecurityConfig {

	// 단방향 암호화(해시) 객체 등록함
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	//시큐리티 필터체인 객체 호출 (접근허가 관련 작업
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	        	.csrf().disable() //공지사항 CRUD가 안먹혀서 추가했습니다. csrf보호 비활성화
	            .authorizeHttpRequests(authorize -> authorize
	            		
	            		.requestMatchers("/site/**","/").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
	            		.requestMatchers("/recomember/temp").permitAll()
	            		.requestMatchers("/recomember/authform").permitAll()
	            		.requestMatchers("/recomember/loginform", "/recomember/login", "/recomember/joinform").permitAll()
	            		.requestMatchers("/recomember/join", "/recomember/healthform", "/recomember/health").permitAll()
	            		.requestMatchers("/recomember/mypage", "/recomember/mypageform").permitAll()
	                .anyRequest().authenticated()             		// 그 외의 요청은 인증 필요
	            )
	            .formLogin(form -> form
	                .loginPage("/recomember/loginform")
	                .successHandler(loginEventHandler())
	                .loginProcessingUrl("/recomember/login")
	                .usernameParameter("id")
	                .passwordParameter("pwd")
	            )
	            .httpBasic(httpBasic -> httpBasic
	                .realmName("FoodFit")                      		// 기본 인증 사용 시 realm 이름 설정
	            );
	        return http.build();
	    }	

	 public AuthenticationSuccessHandler loginEventHandler() {
			return new LoginEventHandler();
		}
}