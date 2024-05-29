package com.sds.foodfit.model.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.MemberDetail;

@Mapper
public interface MemberDetailDAO {
	
	public int  insert(MemberDetail memberDetail);		 // 등록
	public List selectAll();											 // 모두 가져오기
	public MemberDetail select(int memberDetail_idx); // 한건 가져오기
	public void update(MemberDetail memberDetail);	 // 수정하기
	public void delete(MemberDetail memberDetail);    // 삭제하기
}
