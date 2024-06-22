package com.sds.foodfit.model.survey;

import java.util.List;

import com.sds.foodfit.domain.FoodTypeSurvey;

public interface FoodTypeSurveyService {

    public void insertFoodTypeSurvey(FoodTypeSurvey foodTypeSurvey) throws Exception;

    public List<FoodTypeSurvey> selectAll();

}
