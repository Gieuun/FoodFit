package com.sds.foodfit.model.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.DislikedFood;
import com.sds.foodfit.domain.Member;

@Mapper
public interface DislikedFoodDAO {
	
	public int insert(DislikedFood dislikedFood); //등록
	public List selectAll();	
	public DislikedFood select(int dislikedFoodIdx);
	public Member selcteById(String Id);
	
	public void update(DislikedFood dislikedFood);
	
	
}
