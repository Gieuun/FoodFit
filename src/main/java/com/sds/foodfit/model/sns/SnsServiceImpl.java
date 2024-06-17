package com.sds.foodfit.model.sns;

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

	public Sns select(int snsIdx) {
		return snsDAO.select(snsIdx);
	}
}
