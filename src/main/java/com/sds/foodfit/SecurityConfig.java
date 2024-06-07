package com.sds.foodfit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
	
	//시큐리티 필터체인 객체 호출 (접근허가 관련 작업)
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {		 
	        http
	            .authorizeHttpRequests(authorize -> authorize	            		
	            		.requestMatchers("/site/**").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
	            		.requestMatchers("/**").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
	            		.requestMatchers("/food/**").permitAll()	// 추천요청을 인증 없이 허용. 결과물 나오면 수정필요!
	            		.requestMatchers("/rest/notice/**").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
	            		.requestMatchers("/rest/recomember/**").permitAll()
	            		.requestMatchers("/").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
	            		
	                .anyRequest().authenticated()             		// 그 외의 요청은 인증 필요
	            )
	            .formLogin(form -> form
	                .loginPage("/login")
	                .permitAll()                             		// 로그인 페이지는 인증 없이 접근 가능
	            )
	            .httpBasic(httpBasic -> httpBasic
	                .realmName("FoodFit")                      		// 기본 인증 사용 시 realm 이름 설정
	            );

        http.csrf((auth)->auth.disable());
        return http.build();
    }


	 public AuthenticationSuccessHandler loginEventHandler() {
			return new LoginEventHandler();
		}
}