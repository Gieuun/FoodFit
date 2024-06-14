package com.sds.foodfit.model.favoritefood;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.FavoriteFood;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.InsertionFailedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FavoriteFoodServiceImpl implements FavoriteFoodService {

	@Autowired
	private FavoriteFoodDAO favoriteFoodDAO;

	@Override
	public boolean insertFavoriteFood(Member member, FavoriteFood favoriteFood) throws InsertionFailedException {
		favoriteFoodDAO.insertFavoriteFood(member, favoriteFood);
		log.debug("서비스 왔음=====");
		return true;
	}

	@Override
	public FavoriteFood selectUserFavorite(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOnefood(int foodIdx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMultiFood(List<Integer> foodIdxList) {
		// TODO Auto-generated method stub

	}

}
