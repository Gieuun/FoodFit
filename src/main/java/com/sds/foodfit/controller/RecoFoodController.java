package com.sds.foodfit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.foodfit.domain.FoodDB;
import com.sds.foodfit.model.food.FoodDBService;

@Controller
public class RecoFoodController {

	@Autowired
	private FoodDBService foodDBService;

	@GetMapping("/food_recommend_result")
	public String getResult(@RequestParam("type") String type, Model model) {
		List<FoodDB> foods;

		switch (type) {
		case "highProtein" :
			foods=foodDBService.selectHighProtein();
			model.addAttribute("title", "High Protein Foods");
		case "lowSugar" :
			foods=foodDBService.selectLowSugar();
			model.addAttribute("title", "low Sugar Foods");
		case "random" :
			foods=foodDBService.selectRandomHundred();
			model.addAttribute("title", "RandomFoods");
			break;
		default :
			throw new IllegalArgumentException("Invalid food type:"+type);

		}
		model.addAttribute("foods", foods);

		return "/recofood/result";
	}

}
