package com.sds.foodfit.model.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.FoodTypeSurvey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FoodTypeSurveyServiceImpl implements FoodTypeSurveyService {

    @Autowired
    private FoodTypeSurveyDAO foodTypeSurveyDAO;

    @Override
    public void insertFoodTypeSurvey(FoodTypeSurvey foodTypeSurvey) throws Exception {
	try {
	    foodTypeSurveyDAO.insertFoodTypeSurvey(foodTypeSurvey);
	    log.debug("서베이 등록 성공함", foodTypeSurvey.toString());

	} catch (Exception e) {
	    log.debug("insert과정에서 오류발생 : ", e.getMessage());
	}
    }

    @Override
    public List<FoodTypeSurvey> selectAll() {
	return foodTypeSurveyDAO.selectAll();
    }

}
