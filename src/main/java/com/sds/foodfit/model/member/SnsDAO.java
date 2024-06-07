package com.sds.foodfit.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Sns;

@Mapper
public interface SnsDAO {
	
	public Sns select(int snsIdx);
	public Sns selectByName(String snsNname);
}
