package com.sds.foodfit.domain;

import java.util.List;

import lombok.Data;

@Data
public class FavoriteFood { // 멤버의 idx가 필요해서 member를 소요. but 상호참조 아님

    public int favoriteFoodIdx;
    public List<FoodDB> foodDBList; // 1명의 고객은 like한 foodDB 리스트를 1개씩 가지고 있다
    private Member member;

}
