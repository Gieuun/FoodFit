package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class DislikedFood {
	
	private int dislikedFoodIdx;
	private String disfoodName;
	
	private MemberDetail memberDetail; //has a 관계로 회원추가정보 객체 가짐
}
