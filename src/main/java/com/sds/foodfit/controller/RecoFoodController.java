package com.sds.foodfit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sds.foodfit.model.food.FoodDBService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecoFoodController {

	@Autowired
	@Qualifier("recommendFoodService")
	private FoodDBService recommendFoodService;

	@PostMapping("/food/result")
	public ResponseEntity<String> getResultModel(@RequestBody String jsonData, HttpSession session) {
		log.debug("===== 데이터 넘어온 것? =====" + jsonData);

		// 서비스 호출하여 결과 얻기
		Map<String, Object> response = recommendFoodService.setFoodResult(jsonData);

		// 결과를 세션에 저장
		session.setAttribute("foodResult", response);

		log.debug("===== 맵으로 변한 값 확인 =====" + response.toString());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/food/result")
	public String getView(Model model, HttpSession session) {
		// 세션에서 데이터 가져오기
		Map<String, Object> foodResult = (Map<String, Object>) session.getAttribute("foodResult");

		// 모델에 데이터 추가
		if (foodResult != null) {
			model.addAllAttributes(foodResult);
		}

		return "recofood/result";
	}

}