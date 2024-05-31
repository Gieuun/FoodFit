package com.sds.foodfit.model.member;

import com.sds.foodfit.domain.Sns;

public interface SnsService {

	public Sns selectByName(String snsName);
}
