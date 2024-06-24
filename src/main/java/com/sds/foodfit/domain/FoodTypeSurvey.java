package com.sds.foodfit.domain;

import java.sql.Timestamp;

import lombok.Data;

// 서베이 보관용 객체. 추후 필요할 경우 DAO 및 서비스 메서드 구현한다
@Data
public class FoodTypeSurvey {
    private int foodTypeIdx;
    private int subAge;
    private SubGender subGender;
    private FoodType foodType;
    private Timestamp respondDate;

    public enum SubGender {
	male, female
    }

    public enum FoodType {
	highProtein, lowSugar, lowSodium, randomHundred
    }
}