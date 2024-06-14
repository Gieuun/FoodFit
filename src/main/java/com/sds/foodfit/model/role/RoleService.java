package com.sds.foodfit.model.role;

import com.sds.foodfit.domain.Role;

public interface RoleService {

	public Role select(int roleIdx);
	public Role selectByName(String roleName);
}
