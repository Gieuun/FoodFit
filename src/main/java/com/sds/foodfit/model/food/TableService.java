package com.sds.foodfit.model.food;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public interface TableService {
	// 맵핑 데이터 세팅할 메서드
	public ModelAndView setResult(Map<String, String> formData);

}
