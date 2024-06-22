package com.sds.foodfit.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//사용자의 세부 정보를 담는 클래스,  Spring Security의 UserDetails를 구현
@Slf4j
@Data
public class CustomUserDetails implements UserDetails {

    private Member member;

    //생성자 Member 객체를 받아서 CustomUserDetails 객체를 생성
    public CustomUserDetails(Member member) {
	this.member = member;
    }

    //사용자 권한 을 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Collection<GrantedAuthority> authList = new ArrayList();
    	authList.add(new GrantedAuthority() {
    	    public String getAuthority() {

    		log.debug("Role 이름은 " + member.getRole().getRoleName());

    		return member.getRole().getRoleName(); // 홈페이지 회원의 경우 USER
    	    }

    	});
    	return authList;
    }
    // 사용자의 비밀번호를 반환
    @Override
    public String getPassword() {
	return member.getPwd();
    }

    // 사용자의 이름(사용자명을 반환)
    @Override
    public String getUsername() {
	return member.getName();
    }

    // 계정의 만료 여부를 반환 (true이면 만료되지 않음을 의미)
    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    // 계정의 잠김 여부를 반환 (true이면 잠기지 않음을 의미)
    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    // 자격 증명(비밀번호)의 만료 여부를 반환 (true이면 만료되지 않음을 의미)
    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    // 	계정의 활성화 여부를 반환 (true이면 활성화됨을 의미)
    @Override
    public boolean isEnabled() {
	return true;
    }

}