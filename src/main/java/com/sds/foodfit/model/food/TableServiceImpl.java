package com.sds.foodfit.model.food;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.sds.foodfit.domain.FoodDB;

@Service("tableServiceImpl")
public class TableServiceImpl implements FoodDBService{
	  private final FoodDBDAO foodDBDAO;
	  
	    @Autowired
	    public TableServiceImpl(FoodDBDAO foodDBDAO) {
	        this.foodDBDAO = foodDBDAO;
	    }

	@Override
	public Map<String, Object> setFoodResult(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model setTableResult(String jsonData, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	 @Override
	    public List<FoodDB> getAllFoods() {
	        return foodDBDAO.getAllFoods();
	    }

	@Override
	public List<FoodDB> searchFoodsByName(String search) {
		 return foodDBDAO.searchFoodsByName(search);
	}



}