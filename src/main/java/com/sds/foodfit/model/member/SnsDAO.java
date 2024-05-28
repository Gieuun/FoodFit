package com.sds.foodfit.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Sns;

@Mapper
public interface SnsDAO {
	
	public Sns selectByName(String sns_idx);
}
