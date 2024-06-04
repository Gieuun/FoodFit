package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.Sns;

@Service
public class SnsServiceImpl implements SnsService{

	@Autowired
	private SnsDAO snsDAO;
	
	public Sns selectByName(String snsName) {
		
		return snsDAO.selectByName(snsName);
	}
	 
	
}
