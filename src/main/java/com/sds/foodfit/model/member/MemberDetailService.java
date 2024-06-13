package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.MemberDetail;

public interface MemberDetailService {

	public void insert(MemberDetail memberDetail); // 가입

	public MemberDetail selectByMemberIdx(int memberIdx); // idx 해당하는 회원정보가져오기
}
