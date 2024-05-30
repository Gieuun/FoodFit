package com.sds.foodfit.model.food;

import java.util.List;

import com.sds.foodfit.domain.FoodDB;

public interface FoodDBService {
	public void insertFoodDB(FoodDB foodDB);

	public List selectHighProtein();

	public List selectLowSugar();

	public List selectLowSodium();

	public List selectRandomHundred();

}
