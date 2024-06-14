package com.sds.foodfit.model.role;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Role;

@Mapper
public interface RoleDAO {
	
	public Role select(int roleIdx);
	public Role selectByName(String roleName);
	
}
