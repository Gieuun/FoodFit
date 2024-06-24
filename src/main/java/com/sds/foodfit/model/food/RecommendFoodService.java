package com.sds.foodfit.model.food;

import java.util.HashMap;
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

    public List<FoodDB> selectRandomHundred() {
	return foodDBDAO.selectRandomHundred();
    };

    @Override
    public Map<String, Object> setFoodResult(String jsonData) {
	Map<String, Object> response = new HashMap<>();
	Map<String, String> formData;

	try {
	    formData = objectMapper.readValue(jsonData, Map.class);
	} catch (Exception e) {
	    response.put("title", "Error");
	    response.put("foodDBList", null);
	    return response; // Return response immediately in case of error
	}

	String type = formData.get("type");
	List<FoodDB> foodDBList = null; // 초기화 안해주면 오류남

	switch (type) {
	case "highProtein":
	    foodDBList = foodDBDAO.selectHighProtein();
	    response.put("title", "I♥프로틴");
	    break;
	case "lowSugar":
	    foodDBList = foodDBDAO.selectLowSugar();
	    response.put("title", "프로 다이어터");
	    break;
	case "lowSodium":
	    foodDBList = foodDBDAO.selectLowSodium();
	    response.put("title", "네? 저..염?!");
	    break;
	case "random":
	    foodDBList = foodDBDAO.selectRandomHundred();
	    response.put("title", "아무거나 10개 뽑았다");
	    break;
	}

	response.put("foodDBList", foodDBList);
	return response;
    }

    @Override
    public Model setTableResult(String jsonData, Model model) {
	// TODO Auto-generated method stub
	return null;
    }

	@Override
	public List<FoodDB> findByFoodName(String foodName) {
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
