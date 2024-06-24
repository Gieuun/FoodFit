package com.sds.foodfit.model.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.foodfit.domain.Member;



@Mapper
public interface MemberDAO {

	public int insert(Member member); // 등록

	public Member selectById(String id); // id에 해당하는 회원정보 가져오기

	public Member selectByMemberIdx(int memberIdx);

	public int isIdExists(String id); // 아이디 중복 확인

	public void update(Member member); // 회원정보 수정

	public void updateId(int memberIdx, String newId); // 아이디 업데이트
	
	public void deleteMember(int memberIdx); // 회원 삭제
	
	public void updatePassword(int memberIdx, String currentPwd, String newPwd); //비밀번호 변경
	
	public Member selectByEmail(String email); // 이메일로 회원 정보 조회
}