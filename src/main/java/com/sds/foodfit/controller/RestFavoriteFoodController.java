package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.model.favoritefood.FavoriteFoodService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestFavoriteFoodController {

    @Autowired
    private FavoriteFoodService favoriteFoodService;

    @PostMapping("/rest/favoritefood/add")
    public String addFavoriteFood(@RequestParam("foodIdx") int foodIdx, HttpSession session) {
	try {
	    Integer memberIdx = (Integer) session.getAttribute("memberIdx");
	    if (memberIdx == null) {
		throw new RuntimeException("===== 세션에 'memberIdx'가 null");
	    }

	    log.debug("RestFavoriteFood컨트롤러에서 memberIdx : {}", memberIdx);
	    log.debug("RestFavoriteFood컨트롤러에서 foodIdx : {}", foodIdx);

	    favoriteFoodService.insertFavoriteFood(memberIdx, foodIdx);

	    return "즐겨찾기 완료!";
	} catch (Exception e) {
	    log.error("예외 발생 : ", e.getMessage(), e);
	    return "즐겨찾기 추가 중 오류가 발생했습니다.";
	}
    }

}
