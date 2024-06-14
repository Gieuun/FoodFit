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
		log.debug("로그인 검증할 아이디는 " + id);

		Member member = memberDAO.selectById(id);
		log.debug("회원 정보 조회 결과: " + member);

		if (member == null) {
			log.debug("userID =========" + id + "에 해당하는 유저가 존재하지 않습니다.");
			throw new MemberException("일치하는 회원정보가 없습니다.");
		}

		// 회원 idx값 조회
		Integer memberIdx = member.getMemberIdx();
		log.debug("회원의 Idx 정보 : " + memberIdx);

		if (memberIdx == null) {
			log.debug("회원의 idx 정보가 null 입니다 :" + member);
			throw new UsernameNotFoundException("회원의 IDX 정보가 없습니다");
		}
		
		MemberDetail memberDetail =null;
		try {
			// 회원 추가정보 조회
			memberDetail = memberDetailDAO.selectByMemberIdx(memberIdx);
			log.debug("회원의 상세 정보 조회 결과: " + memberDetail);
			member.setMemberDetail(memberDetail);
		} catch (Exception e) {
			log.error("회원 상제 정보 조회 중 오류 발생", e);
			throw new UsernameNotFoundException("회원 상세 정보 조회 중 오류 발생", e);
		}

		FavoriteFood favoriteFood =null;
		try {
			// 선호 음식 정보 조회
			favoriteFood = favoriteFoodDAO.selectUserFavorite(memberIdx);
			log.debug("마이푸드 정보 조회 결과: " + favoriteFood);
			
		} catch (Exception e) {
			log.error("선호 음식 정보 조회 중 오류 발생", e);
			throw new UsernameNotFoundException("선호 음식 정보 조회 중 오류 발생", e);
			
		}

		// UserDetails로 변환하여 반환
		UserDetails userDetails = new CustomUserDetails(member, memberDetail, favoriteFood);

		// 변환된 UserDetails를 로그 기록
		log.debug("로그인 검증 완료한 UserDetails: " + userDetails);
		
		log.debug("member found : {}" + member);
		log.debug("멤버디테일 =====" +memberDetail);
		log.debug("마이푸드 =====" +favoriteFood);
		return userDetails;
	}

}