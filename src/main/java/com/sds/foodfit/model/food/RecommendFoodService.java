package com.sds.foodfit.model.food;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.foodfit.domain.FoodDB;

@Service
public class RecommendFoodService implements FoodDBService {

	@Autowired
	private FoodDBDAO foodDBDAO;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Model setFoodResult(String jsonData, Model model) {
		Map<String, String> formData;

		try {
			formData = objectMapper.readValue(jsonData, Map.class);
		} catch (Exception e) {
			model.addAttribute("title", "Error");
			model.addAttribute("foods", null);
			return model; // Return model immediately in case of error
		}

		String type = formData.get("type");
		List<FoodDB> foods;

		switch (type) {
		case "highProtein":
			foods = foodDBDAO.selectHighProtein();
			model.addAttribute("title", "I♥프로틴");
			break;
		case "lowSugar":
			foods = foodDBDAO.selectLowSugar();
			model.addAttribute("title", "프로 다이어터");
			break;
		case "random":
			foods = foodDBDAO.selectLowSodium();
			model.addAttribute("title", "네? 저..염?!");
			break;
		default:
			foods = foodDBDAO.selectRandomHundred();
			model.addAttribute("title", "다이나믹하게 아무거나 100개");
			break;
		}

		model.addAttribute("foods", foods);

		return model;
	}

	@Override
	public Model setTableResult(String jsonData, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

}
