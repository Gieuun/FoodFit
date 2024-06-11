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
	
	@GetMapping("table/result") //결과 페이지 요청처리
	public String getResult(@RequestParam("type") String type, Model model) {
		List<FoodDB> foods;

		switch (type) {
		case "breakfast" :
			foods=foodDBService.selectBreakfast();
			model.addAttribute("title", "아침");
		case "lunch" :
			foods=foodDBService.selectLunch();
			model.addAttribute("title", "점심");
		case "dinner" :
			foods=foodDBService.selectDinner();
			model.addAttribute("title", "저녁");
			break;
		default :
			throw new IllegalArgumentException("Invalid food type:"+type);

		}
		model.addAttribute("foods", foods);

		return "/recotable/result";
	}
}
