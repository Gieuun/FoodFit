package com.sds.foodfit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sds.foodfit.model.food.FoodDBService;

@Controller
public class RecoTableController {

	// 여기서 view 작업 위주

	@Autowired
	@Qualifier("calculateFoodService")
	private FoodDBService calculateFoodService;

	@PostMapping("/table/result")
	public String setResult(@RequestBody Map<String, String> formData) {
		return "recotable/result";
	}

}
