package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberDAO memberDAO;
	
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException, MemberException {
		Member member= memberDAO.selectByid(id);
		
		if(member ==null) {
			throw new MemberException("일치하는 회원정보가 없습니다.");
		}
		
		return new CustomUserDetails(member);
	}

}
