package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sds.foodfit.domain.FavoriteFood;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.InsertionFailedException;
import com.sds.foodfit.model.favoritefood.FavoriteFoodService;

@RestController
public class RestRecofoodController {

	@Autowired
	private FavoriteFoodService favoriteFoodService;

	// 즐겨찾기를 할때 비동기 방식으로 유저의 favoriteFood에 음식idx를 저장함

	@PostMapping("/add-to-favorites")
	public ResponseEntity<String> insertFavoriteFood(@SessionAttribute("member") Member member,
			@RequestBody FavoriteFood favoriteFood) {
		try {
			favoriteFoodService.insertFavoriteFood(member, favoriteFood);
			return ResponseEntity.ok("Favorite food inserted successfully");
		} catch (InsertionFailedException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Favorite food insertion failed");
		}
	}
}
