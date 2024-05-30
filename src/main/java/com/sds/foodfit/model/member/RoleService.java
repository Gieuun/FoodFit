package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.Role;

public interface RoleService {

	public Role selectByName(String role_name);
}
