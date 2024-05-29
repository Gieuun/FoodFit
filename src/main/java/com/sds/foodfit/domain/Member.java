package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Member {
	private int memberIdx;
	private String name;
	private String id;
	private String pwd;
	private String email;
	private String role;
	
	private Sns sns; //has a 관계로 부모를 보유
	private MemberDetail memberDetail; // 회원추가정보객체 가짐

}
