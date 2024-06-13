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
		FavoriteFood favoriteFood = favoriteFoodDAO.selectUserFavorite(id);

		log.debug("member found : {}", member);
		return new CustomUserDetails(member, memberDetail, favoriteFood);
	}

}