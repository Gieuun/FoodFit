package com.sds.foodfit.domain;


import lombok.Data;

@Data
public class FoodApiResponse {
	private String resultCode;
	private String resultMsg;
	private String numOfRows;
	private String pageNo;
	private String totalCount;
	private FoodDB foodDB;	// food객체를 가짐
	

}
