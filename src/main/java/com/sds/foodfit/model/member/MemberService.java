package com.sds.foodfit.model.member;


import com.sds.foodfit.domain.Member;

public interface MemberService {

	public void regist(Member member); // 회원 가입

    public Member selectById(String id); // id에 해당하는 회원정보 가져오기

    public Member selectByMemberIdx(int memberIdx); // 회원 번호에 해당하는 회원정보 가져오기

    public boolean isIdExists(String id); // 아이디 중복 확인

    public void update(Member member); // 회원정보 수정
    
    public void updateId(int memberIdx, String newId); // 아이디 업데이트
    
    public void deleteMember(int memberIdx); // 회원 삭제
    
    public void updatePassword(int memberIdx, String currentPwd, String newPwd); //비밀번호 변경
    
    public boolean isEmailExists(String email); // 이메일 중복 확인
  
}