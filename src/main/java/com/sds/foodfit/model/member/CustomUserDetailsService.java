package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.exception.FavoriteFoodException;
import com.sds.foodfit.exception.MemberDetailException;
import com.sds.foodfit.exception.MemberException;
import com.sds.foodfit.model.favoritefood.FavoriteFoodDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberDAO memberDAO;
    
    @Autowired
    private MemberDetailDAO memberDetailDAO;

	@Autowired
	private FavoriteFoodDAO favoriteFoodDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	//생성자 주입
	public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder =passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException, MemberException ,FavoriteFoodException {
		
		log.debug("로그인 검증할 아이디는 " + id);

		Member member = memberDAO.selectById(id);
		log.debug("회원 정보 : " + member);
		
		if (member == null) {
			throw new MemberException("일치하는 회원정보가 없습니다.");
		}
		
		log.debug("db 에 있는 비번은 "+ member.getPwd());
		log.debug("새롭게 생성된 비번은 "+ passwordEncoder.encode(member.getPwd()));
		

		// 회원 idx값 조회
	    Integer memberIdx = member.getMemberIdx();
		log.debug("회원의 Idx 정보 : " + memberIdx);
		
		MemberDetail memberDetail = memberDetailDAO.selectByMemberIdx(memberIdx);
		log.debug("회원 추가정보 : "+ memberDetail);
		
		if(memberDetail ==null) {
			throw new MemberDetailException("일치하는 상세정보가 없습니다");
		}
	
		/* 데이터가가 저장이 안되서 이걸 포함시켜서 넘기면 로그인 처리안되고 주석으로 남겨서 하면 
		    로그인 처리 잘된다.. 여기만 해결하면 되는데,,
		FavoriteFood favoriteFood = favoriteFoodDAO.selectUserFavorite(memberIdx);
		log.debug("마이푸드 정보 조회 결과: " + favoriteFood);
		if(favoriteFood == null) {
			throw new FavoriteFoodException("일치하는 마이 푸드가 없습니다.");
		}
		*/
		return new CustomUserDetails(member, memberDetail, null);
    }

}