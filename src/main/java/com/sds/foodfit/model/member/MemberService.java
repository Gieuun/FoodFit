package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;

public interface MemberService {

	public void regist(MemberDetail memberDetail); // 가입

	public Member selectById(String id); // id에 해당하는 회원정보 가져오기
	

}