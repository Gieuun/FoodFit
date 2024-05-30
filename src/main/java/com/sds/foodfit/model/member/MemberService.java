package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.Member;

public interface MemberService {

	public void regist(Member member); 			// 가입
	public Member selectByid(String id); 		// id에 해당하는 회원정보 가져오기
}
