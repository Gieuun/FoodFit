package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

// CustomUserDetails에서 구현한 사용자 정보를 로드하는 서비스
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final MemberDAO memberDAO;

    private final PasswordEncoder passwordEncoder;

    // 생성자 주입
    public CustomUserDetailsService(PasswordEncoder passwordEncoder, MemberDAO memberDAO) {
	this.passwordEncoder = passwordEncoder;
	this.memberDAO  = memberDAO;
    }

    //사용ㅇ자 ID 또는 사용자명으로 사용자 정보를 로드하는 메서드
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException, MemberException {
	log.debug("로그인 검증할 아이디는 " + id);

	// DB에서 ID로 사용자 정보 조회
	Member member = memberDAO.selectById(id);
	log.debug("회원 정보 : " + member);

	if (member == null) {
	    throw new MemberException("일치하는 회원정보가 없습니다.");
	}

	log.debug("db 에 있는 비번은 " + member.getPwd());
	log.debug("새롭게 생성된 비번은 " + passwordEncoder.encode(member.getPwd()));

	// 회원 idx값 조회
	Integer memberIdx = member.getMemberIdx();
	log.debug("회원의 Idx 정보 : " + memberIdx);

	// CustomUserDetails 객체 생성 및 반환
	CustomUserDetails userDetails = new CustomUserDetails(member);

	return userDetails;

    }
}