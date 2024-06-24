package com.sds.foodfit.model.food;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.foodfit.domain.FoodDB;

@Mapper
public interface FoodDBDAO { // 여기에 FoodDB select 관련로직 다 넣으면 됨

    public FoodDB selectOneFood(int foodIdx);

    public List<FoodDB> selectHighProtein();

    public List<FoodDB> selectLowSugar();

    public List<FoodDB> selectLowSodium();

    public List<FoodDB> selectRandomHundred();

    public int sumKcalByFoodIdx(@Param("foodIdxList") List<Integer> foodIdxList);

    public int sumCarbohydrateByFoodIdx(@Param("foodIdxList") List<Integer> foodIdxList);

    public int sumProteinByFoodIdx(@Param("foodIdxList") List<Integer> foodIdxList);

    public int sumFatByFoodIdx(@Param("foodIdxList") List<Integer> foodIdxList);

    public List<FoodDB> getAllFoods(); // 밥상진단- 음식 이름 가져오기

    public List<FoodDB> searchFoodsByName(@Param("search") String search);

    public List<FoodDB> findByFoodName(@Param("foodName") String foodName);

}