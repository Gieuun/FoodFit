package com.sds.foodfit.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Role;

@Mapper
public interface RoleDAO {
	
	public Role selectByName(String role_name);
	
}
