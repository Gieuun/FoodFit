package com.sds.foodfit.model.food;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.foodfit.domain.FoodDB;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("recommendFoodService")
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
			model.addAttribute("foodDBList", null);
			return model; // Return model immediately in case of error
		}

		String type = formData.get("type");
		List<FoodDB> foodDBList;

		switch (type) {
		case "highProtein":
			foodDBList = foodDBDAO.selectHighProtein();
			model.addAttribute("title", "I♥프로틴");
			break;
		case "lowSugar":
			foodDBList = foodDBDAO.selectLowSugar();
			model.addAttribute("title", "프로 다이어터");
			break;
		case "random":
			foodDBList = foodDBDAO.selectLowSodium();
			model.addAttribute("title", "네? 저..염?!");
			break;
		default:
			foodDBList = foodDBDAO.selectRandomHundred();
			model.addAttribute("title", "다이나믹하게 아무거나 100개");
			break;
		}

		model.addAttribute("foodDBList", foodDBList);
		log.debug("=====setFoodResult 메서드 돌아감 =====" + model.toString());

		return model;
	}

	@Override
	public Model setTableResult(String jsonData, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

}
