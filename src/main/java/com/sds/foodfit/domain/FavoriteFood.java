package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class FavoriteFood { // 멤버의 idx가 필요해서 member를 소요. but 상호참조 아님

    public int favoriteFoodIdx;
    public FoodDB foodDB; // like한 food객체들 가지고 있음
    private Member member;

}