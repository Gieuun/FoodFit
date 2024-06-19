package com.sds.foodfit.model.favoritefood;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.FavoriteFood;
import com.sds.foodfit.domain.FoodDB;
import com.sds.foodfit.domain.Member;
import com.sds.foodfit.exception.InsertionFailedException;
import com.sds.foodfit.model.food.FoodDBDAO;
import com.sds.foodfit.model.member.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FavoriteFoodServiceImpl implements FavoriteFoodService {

    @Autowired
    private FavoriteFoodDAO favoriteFoodDAO;

    @Autowired
    private FoodDBDAO foodDBDAO;

    @Autowired
    private MemberDAO memberDAO;

    @Override
    public boolean insertFavoriteFood(Member member, FavoriteFood favoriteFood) throws InsertionFailedException {
	favoriteFoodDAO.insertFavoriteFood(member, favoriteFood);
	log.debug("서비스 왔음=====");
	return true;
    }

    @Override
    public FavoriteFood selectByMemberIdx(int memberIdx) {
	FavoriteFood favoriteFood = favoriteFoodDAO.selectByMemberIdx(memberIdx);
	int water = 14137; // 물을 디폴트 객체로 설정

	if (favoriteFood == null) {
	    FoodDB defaultFood = foodDBDAO.selectOneFood(water);
	    Member member = memberDAO.selectByMemberIdx(memberIdx);
	    favoriteFoodDAO.insertFavoriteFood(member, favoriteFood);
	}

	return favoriteFood;
    }

    @Override
    public void deleteOnefood(int foodIdx) {
	favoriteFoodDAO.deleteOnefood(foodIdx);
    }

    @Override
    public void deleteMultiFood(List<Integer> foodIdxList) {
	favoriteFoodDAO.deleteMultiFood(foodIdxList);
    }
}
