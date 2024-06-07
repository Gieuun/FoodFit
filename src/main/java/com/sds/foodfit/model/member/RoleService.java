package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.Role;

public interface RoleService {

	public Role select(int roleIdx);
	public Role selectByName(String roleName);
}
