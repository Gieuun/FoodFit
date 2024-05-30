package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.MemberException;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberDAO memberDAO;
	
	//메서드로 전달되는 매개변수 값을 이용하여, db에 회원이 존재하면 정보를 가져오는 메서드 
	//주의) 매개변수명은 이미 정해져 잇다...따라서 매개변수명을 각 시스템 상황에 맞게 바꾸려면, 보안설정 빈 등록 클래스에서 추가 설정이필요..
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException, MemberException {
		Member member = memberDAO.selectByid(id);
		
		if(member ==null) {
			throw new MemberException("일치하는 회원정보가 없습니다.");
		}
		
		return new CustomUserDetails(member);
	}

}
