package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class MemberDetail {
	private int memberDetailIdx;
	private String gender;
	private int age;
	private double height;
	private double weight;
	private Member member; // 회원정보 객체 가짐

	private FavoriteFood favoriteFood; // 비선호 음식 객체 가짐

}
