package com.sds.foodfit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("user".equals(username)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername("guest")
                    .password("{noop}password") // {noop}은 비밀번호를 암호화하지 않음을 의미합니다.
                    .roles("guest")
                    .build();
        } else {
            throw new UsernameNotFoundException("guest not found");
        }
	}
	

}
