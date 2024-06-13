package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class FavoriteFood {

	public int favoriteFoodIdx;
	public FoodDB foodDB; // like한 food객체들 가지고 있음
	private Member member;
}
