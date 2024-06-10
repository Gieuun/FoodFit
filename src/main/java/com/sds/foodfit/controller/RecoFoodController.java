package com.sds.foodfit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sds.foodfit.model.food.FoodDBService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecoFoodController {

	@Autowired
	@Qualifier("recommendFoodService")
	private FoodDBService recommendFoodService;

	@PostMapping("/food/result")
	public ResponseEntity<Map<String, Object>> getResultModel(@RequestBody String jsonData) {
		log.debug("===== 데이터 넘어온 것? =====" + jsonData);
		// 서비스 호출하여 결과 얻기
		Model model = new ExtendedModelMap();
		recommendFoodService.setFoodResult(jsonData, model);

		// Model을 Map으로 변환
		Map<String, Object> response = model.asMap();

		// 결과 반환
		log.debug("===== 맵으로 변한 값 확인 =====" + response.toString());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/food/result")
	public String getView() {
		return "recofood/result";
	}

}
