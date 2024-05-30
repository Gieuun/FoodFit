package com.sds.foodfit.model.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.Member;

@Service
public class AdminService {
	
	@Autowired
	private AdminDAO adminDAO;
	
	public Member findAdmin() {
		return adminDAO.findAdmin("admin");
	}

}
