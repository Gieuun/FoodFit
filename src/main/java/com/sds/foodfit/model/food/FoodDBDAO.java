package com.sds.foodfit.model.food;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.FoodDB;

@Mapper
public interface FoodDBDAO {
	public void insertFoodDB(FoodDB foodDB);

	public List selectHighProtein();

	public List selectLowSugar();

	public List selectLowSodium();

	public List selectRandomHundred();

}
