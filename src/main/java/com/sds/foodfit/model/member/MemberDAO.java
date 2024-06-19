package com.sds.foodfit.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Member;



@Mapper
public interface MemberDAO {

    public int insert(Member member); // 등록

    public Member selectById(String id); // id에 해당하는 회원정보 가져오기

    public Member selectByMemberIdx(int memberIdx);
    
    public int isIdExists(String id); // 아이디 중복 확인
    
    public void update(Member member); // 회원정보 수정
  
	//public int delete(int memberIdx);  //회원탈퇴

}