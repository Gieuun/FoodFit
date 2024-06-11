package com.sds.foodfit.model.food;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.FoodDB;

@Service
public class FoodDBServiceImpl implements FoodDBService {

	@Autowired
	private FoodDBDAO foodDBDAO;

	@Override
	public List selectHighProtein() {
		// TODO Auto-generated method stub
		return foodDBDAO.selectHighProtein();
	}

	@Override
	public List selectLowSugar() {
		return foodDBDAO.selectLowSugar();
	}

	@Override
	public List selectLowSodium() {
		// TODO Auto-generated method stub
		return foodDBDAO.selectLowSodium();
	}

	@Override
	public List selectRandomHundred() {
		return foodDBDAO.selectRandomHundred();
	}

}
