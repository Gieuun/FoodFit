package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.domain.FoodTypeSurvey;
import com.sds.foodfit.model.survey.FoodTypeSurveyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestSurveyController {

    @Autowired
    private FoodTypeSurveyService foodTypeSurveyService;

    @PostMapping("/rest/foodTypeSurvey/regist")
    public ResponseEntity<String> regist(@Validated @RequestBody FoodTypeSurvey foodTypeSurvey, BindingResult result) {
	if (result.hasErrors()) {
	    // 내부 로깅 처리
	    for (FieldError error : result.getFieldErrors()) {
		// 예: 로그로 남기기
		log.debug("서베이기록 오류: " + error.getField() + " - " + error.getDefaultMessage());
	    }
	    // 유저에게는 간단한 메시지 반환
	    return ResponseEntity.badRequest().body("로딩 중 오류. 이용에 불편을 드려 죄송합니다 ");
	}

	try {
	    foodTypeSurveyService.insertFoodTypeSurvey(foodTypeSurvey);
	    // 내부 추가 처리 (필요시)
	    // 예: 성공 메시지 로그
	    log.debug("서베이 등록 성공: " + foodTypeSurvey.toString());
	    return ResponseEntity.ok("입력값을 확인했습니다");
	} catch (Exception e) {
	    // 예외 상세 정보 로깅
	    e.printStackTrace();
	    // 유저에게는 간단한 메시지 반환
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버예외 발생. 이용에 불편을 드려 죄송합니다");
	}
    }
}