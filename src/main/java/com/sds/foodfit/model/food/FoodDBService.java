package com.sds.foodfit.model.food;

import java.util.Map;

import org.springframework.ui.Model;

public interface FoodDBService { // 이 인터페이스는 음식과 밥상에서 공통으로 사용
	// 맵핑 데이터 세팅할 메서드

	public Map<String, Object> setFoodResult(String jsonData); // 밥상기능

	public Model setTableResult(String jsonData, Model model); // 밥상기능

}
