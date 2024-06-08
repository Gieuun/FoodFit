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
public class RecoTableController {

	@Autowired
	private FoodDBService foodDBService;

	@GetMapping("/recotable/result")
	public String getTableResult() {
		return "recotable/result";
	}
	
	@GetMapping("/recotable/example")
	public String getExample() {
		return "recotable/example";
	}

}
