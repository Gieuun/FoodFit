package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class FoodDB {
	public int food_idx;
	public String foodnm;
	public int kcal;
	public int carboh;
	public int protein;
	public int liquid;
	public Member member;
	
}
