package com.sds.foodfit.model.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.Role;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDAO roleDAO;
	
	public Role selectByName(String roleName) {
		return roleDAO.selectByName(roleName);
	}

	public Role select(int roleIdx) {
		return roleDAO.select(roleIdx);
	}

}
