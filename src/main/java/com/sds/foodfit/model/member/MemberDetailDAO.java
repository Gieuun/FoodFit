package com.sds.foodfit.model.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.foodfit.domain.MemberDetail;

@Mapper
public interface MemberDetailDAO {

	public int insert(MemberDetail memberDetail); // 등록

	public MemberDetail selectByMemberIdx(int memberIdx); // memberIdx 해당하는 회원정보 가져오기
}
