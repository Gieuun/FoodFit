package com.sds.foodfit.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NutrientManager {

	@Autowired
	private ObjectMapper objectMapper;

	// 여기서 할것 -> 공통으로 쓰는 BMR과 TDEE공식을 분리해서 별도 매서드로 짠다
	public Model calulateKcal(String jsonData, Model calculatedKcal) {
		Map<String, Object> formData;

		try {
			formData = objectMapper.readValue(jsonData, Map.class);
		} catch (Exception e) {
			calculatedKcal.addAttribute("title", "에러발생");
			calculatedKcal.addAttribute("errorMessage", "JSON 양식에 오류가 있습니다");
			return calculatedKcal;
		}

		// ======json넣은 변수 넣어주면 됨.

		String gender = (String) formData.get("gender");
		int age = (int) formData.get("age");
		double height = (double) formData.get("height");
		double weight = (double) formData.get("weight");

		// 날라온 키, 몸무게 <- 데이터에서 날라온다 (input단계에서 memberDetail 활용하고 여기서는 날아온 값만 이용하자)

		// BMR (기초대사량 기준 칼로리) 공식대입

		double BMR;

		// =======================

		if (gender.equals("male")) { // 남자라면
			BMR = 10 * weight + 6.25 * height - 5 * age + 5;
		} else { // 여자라면
			BMR = 10 * weight + 6.25 * height - 5 * age - 161;
		}

		// TDEE (활동대입 기준 칼로리) ; 여기에서는 1.55을 공통계수로 기준을 잡음.
		double TDEE = BMR * 1.55;

		// 아침, 점심, 저녁 칼로리 비율을 계산 (식단에서 권장하는 비율을 적용)
		double breakfastCalories = TDEE * 0.25;
		double lunchCalories = TDEE * 0.4;
		double dinnerCalories = TDEE * 0.35;
		// 탄수화물, 단백질, 지방

		calculatedKcal.addAttribute("BMR", BMR);
		calculatedKcal.addAttribute("TDEE", TDEE);
		calculatedKcal.addAttribute("breakfastCalories", breakfastCalories);
		calculatedKcal.addAttribute("lunchCalories", lunchCalories);
		calculatedKcal.addAttribute("dinnerCalories", dinnerCalories);

		return calculatedKcal;

	}

}
