package com.sds.foodfit.model.survey;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.FoodTypeSurvey;

@Mapper
public interface FoodTypeSurveyDAO {

    public void insertFoodTypeSurvey(FoodTypeSurvey foodTypeSurvey);

    public List<FoodTypeSurvey> selectAll();

}
