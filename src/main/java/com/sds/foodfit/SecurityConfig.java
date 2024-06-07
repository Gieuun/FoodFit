package com.sds.foodfit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 단방향 암호화(해시) 객체 등록함
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//시큐리티 필터체인 객체 호출 (접근허가 관련 작업
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            		.requestMatchers("/site/**").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
            		.requestMatchers("/**").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
            		.requestMatchers("/").permitAll()	// 모든 요청을 인증 없이 허용. 결과물 나오면 수정필요!
            		.requestMatchers("/rest/recomember/**").permitAll()

            		.requestMatchers("/admin/**").hasRole("ADMIN")
            		
                .anyRequest().authenticated()             		// 그 외의 요청은 인증 필요
            )
            .formLogin(form -> form
                .loginPage("/recomember/loginform")
                .successHandler(loginEventHandler())
                .loginProcessingUrl("/recomember/login")
                .usernameParameter("id")
                .passwordParameter("pwd")
                .permitAll()                             		// 로그인 페이지는 인증 없이 접근 가능
            )
            
            .httpBasic(httpBasic -> httpBasic
                .realmName("FoodFit")                      		// 기본 인증 사용 시 realm 이름 설정
            );

        //LoginFilter loginFilter = new LoginFilter(authenticationManager());
        //loginFilter.setFilterProcessesUrl("/recomember/login"); 
        //http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        
        
        
        //http.csrf((auth)->auth.disable());
        return http.build();
    }
	 // 업로드용 관리자계정 권한설정
	    @Bean
	    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
	        UserDetails admin = User.builder()
	            .username("admin")
	            .password(passwordEncoder.encode("password"))
	            .roles("ADMIN")
	            .build();

	        return new InMemoryUserDetailsManager(admin);
	    }
	    
	 
	 public AuthenticationSuccessHandler loginEventHandler() {
			return new LoginEventHandler();
		}
	 
}