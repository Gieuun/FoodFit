package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.FavoriteFood;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
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

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException, MemberException {
	log.debug("넘겨 받은 ID : {}", id);

	if (id == null || id.isEmpty()) {
	    log.error("Received null or empty ID");
	    throw new UsernameNotFoundException("ID is null or empty");
	}

	Member member = memberDAO.selectById(id);

	if (member == null) {
	    log.error("member found with ID: {}", id);
	    throw new MemberException("일치하는 회원정보가 없습니다.");
	}

	MemberDetail memberDetail = memberDetailDAO.selectByMemberIdx(member.getMemberIdx());
	// 얘는 회원가입때 같이 받은 정보. ->
	// 1. 분리하거나 2. 메서드 문제 해결하거나

	FavoriteFood favoriteFood = favoriteFoodDAO.selectUserFavorite(member.getMemberIdx());
	if (favoriteFood == null) {
	    log.warn("마이푸드가 없습니다. 빈 객체를 생성합니다.", id);
	    favoriteFood = new FavoriteFood(); // 빈 객체 생성 또는 기본값 설정
	}

	log.debug("member found : {}", member);
	log.debug("멤버디테일 =====", memberDetail);
	log.debug("페이보릿푸드 =====", favoriteFood);

	return new CustomUserDetails(member, memberDetail, favoriteFood);
    }

}