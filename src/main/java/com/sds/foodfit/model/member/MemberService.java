package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.Member;

public interface MemberService {

	void regist(Member member); // 회원 가입

    Member selectById(String id); // id에 해당하는 회원정보 가져오기

    Member selectByMemberIdx(int memberIdx);

    boolean isIdExists(String id); // 아이디 중복 확인

    void update(Member member); // 회원정보 수정

    //public void delete(int memberIdx);  //회원탈퇴
}