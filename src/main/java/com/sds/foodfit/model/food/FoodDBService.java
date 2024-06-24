package com.sds.foodfit.model.food;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.sds.foodfit.domain.FoodDB;

public interface FoodDBService { // 이 인터페이스는 음식과 밥상에서 공통으로 사용
    // 맵핑 데이터 세팅할 메서드

    public Map<String, Object> setFoodResult(String jsonData); // 밥상기능

    public Model setTableResult(String jsonData, Model model); // 밥상기능

    List<FoodDB> getAllFoods();

    List<FoodDB> searchFoodsByName(String search);

    List<FoodDB> findByFoodName(String foodName);

}