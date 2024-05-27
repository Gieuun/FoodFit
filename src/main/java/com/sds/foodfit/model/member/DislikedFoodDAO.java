package com.sds.foodfit.model.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.DislikedFood;

@Mapper
public interface DislikedFoodDAO {
	
	public int insert(DislikedFood dislikedFood);
	public List selectAll();
	public DislikedFood select(int dislikedFood_idx);
	public void update(DislikedFood dislikedFood);
	
	
}
