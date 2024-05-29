package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Allergy {
	
	private int allergy_idx;
	private String allergy_name;			 // 알러지 이름
	private MemberDetail memberDetail; // has a 관계로 회원추가 정보 객체 가짐
	private Member member;				 // has a 관계로 회원 정보 객체 가짐
}
