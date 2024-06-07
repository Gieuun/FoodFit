package com.sds.foodfit.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

//회원의 상세정보를 가진 객체 
@Data
public class CustomUserDetails  implements UserDetails{
	
	private Member member;
	
	public CustomUserDetails(Member member) {
		this.member = member;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authList = new ArrayList();
		authList.add(new GrantedAuthority() {
			public String getAuthority() {
				return member.getRole().getRoleName(); // 홈페이지 회원 경우 USER
			}
		});
		return authList;
	}

	@Override
	public String getPassword() {
		return member.getPwd();
	}

	@Override
	public String getUsername() {
		return member.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
