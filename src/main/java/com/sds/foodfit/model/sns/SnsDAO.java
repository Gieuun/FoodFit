package com.sds.foodfit.model.sns;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Sns;

@Mapper
public interface SnsDAO {
	
	public Sns select(int snsIdx);
	public Sns selectByName(String snsNname);
}
