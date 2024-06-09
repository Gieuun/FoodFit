package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.sds.foodfit.model.food.FoodDBService;

@Controller
public class RecoFoodController {

	// 여기서 view 작업 위주

	@Autowired
	@Qualifier("recommendFoodService")
	private FoodDBService recommendFoodService;

}
