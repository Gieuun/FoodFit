package com.sds.foodfit.model.food;

import java.util.List;

import com.sds.foodfit.domain.FoodDB;

public interface FoodDBService {

	public List selectHighProtein();

	public List selectLowSugar();

	public List selectLowSodium();

	public List selectRandomHundred();
	
	public List selectBreakfast();
	
	public List selectLunch();
	
	public List selectDinner();
	

}
