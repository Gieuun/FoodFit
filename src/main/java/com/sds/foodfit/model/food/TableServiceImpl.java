package com.sds.foodfit.model.food;

import com.sds.foodfit.domain.FoodDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service("tableServiceImpl")
public class TableServiceImpl implements FoodDBService {

    private final FoodDBDAO foodDBDAO;

    @Autowired
    public TableServiceImpl(FoodDBDAO foodDBDAO) {
        this.foodDBDAO = foodDBDAO;
    }

    @Override
    public List<FoodDB> getAllFoods() {
        return foodDBDAO.getAllFoods();
    }

    @Override
    public List<FoodDB> searchFoodsByName(String search) {
        return foodDBDAO.searchFoodsByName(search);
    }

    @Override
    public List<FoodDB> findByFoodName(String foodName) {
        return foodDBDAO.findByFoodName(foodName);
    }

    @Override
    public Map<String, Object> setFoodResult(String jsonData) {
        // jsonData를 처리하고, 필요한 데이터 반환하는 로직 구현
        return null;
    }

    @Override
    public Model setTableResult(String jsonData, Model model) {
        // jsonData를 처리하고, 모델에 데이터 추가하는 로직 구현
        return model;
    }
}