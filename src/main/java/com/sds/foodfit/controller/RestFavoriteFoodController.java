package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.domain.FavoriteFood;
import com.sds.foodfit.model.favoritefood.FavoriteFoodService;

@RestController
public class RestFavoriteFoodController {

    @Autowired
    private FavoriteFoodService favoriteFoodService;

    @GetMapping("/api/favoriteFood/{memberIdx}")
    public ResponseEntity<FavoriteFood> getFavoriteFood(@PathVariable int memberIdx) {
	FavoriteFood favoriteFood = favoriteFoodService.selectByMemberIdx(memberIdx);
	return ResponseEntity.ok(favoriteFood);
    }

}
