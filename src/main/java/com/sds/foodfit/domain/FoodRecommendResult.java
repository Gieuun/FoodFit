package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class FoodRecommendResult {
	public int food_recommend_res_idx;
	public String recommend_res_foodnm;
	public int carbohydrate;
	public int protain;
	public int fat;
	public int kcal;
	public Member member;

}
