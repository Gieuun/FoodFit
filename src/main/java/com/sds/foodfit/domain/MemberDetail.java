package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class MemberDetail {
	private int memberDetailIdx;
	private String gender;
	private int age;
	private long height;
	private long weight;
	
	private Member member; // 회원정보 객체 가짐

}
