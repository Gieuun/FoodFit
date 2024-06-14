package com.sds.foodfit.model.food;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.foodfit.domain.FoodDB;

@Service("calculateFoodService")
public class CalculateFoodService implements FoodDBService {

	@Autowired
	private FoodDBDAO foodDBDAO;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Model setTableResult(String jsonData, Model model) {
		// 이거 유저 먹은거 넘버링 들어오는거
		Map<String, Object> formData;

		try {
			formData = objectMapper.readValue(jsonData, Map.class);
		} catch (Exception e) {
			model.addAttribute("title", "Error");
			model.addAttribute("errorMessage", "Invalid JSON data");
			return model;
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

		int sumKcal = foodDBDAO.sumKcalByFoodIdx(null); // null 대신 index집합 넣어야 함.

		// Adding attributes to model
		model.addAttribute("BMR", BMR);
		model.addAttribute("TDEE", TDEE);
		model.addAttribute("sumKcal", sumKcal);

		return model;
	}

	@Override
	public Map<String, Object> setFoodResult(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodDB> getAllFoods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodDB> searchFoodsByName(String search) {
		// TODO Auto-generated method stub
		return null;
	}

}