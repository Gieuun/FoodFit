package com.sds.foodfit.model.favoritefood;

import java.util.Collections;
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
    public boolean insertFavoriteFood(int memberIdx, int foodIdx) throws InsertionFailedException {
	favoriteFoodDAO.insertFavoriteFood(memberIdx, foodIdx);
	log.debug("서비스 왔음=====");
	return true;
    }

    @Override
    public List<FavoriteFood> selectByMemberIdx(int memberIdx) {

	// 물을 디폴트 객체로 설정
	int water = 14137;
	FoodDB defaultFood = foodDBDAO.selectOneFood(water);

	List<FavoriteFood> favoriteFoodList = favoriteFoodDAO.selectByMemberIdx(memberIdx);

	// 즐겨찾기가 비었다면, 물을 넣어준다
	if (favoriteFoodList.isEmpty()) {
	    Member member = memberDAO.selectByMemberIdx(memberIdx);
	    FavoriteFood favoriteFood = new FavoriteFood();
	    favoriteFood.setFoodDBList(Collections.singletonList(defaultFood));
	    favoriteFood.setMember(member);

	    // 새로운 즐겨찾기 추가
	    favoriteFoodDAO.insertFavoriteFood(memberIdx, water);

	    // 추가된 즐겨찾기 목록 조회
	    favoriteFoodList = favoriteFoodDAO.selectByMemberIdx(memberIdx);

	}

	return favoriteFoodList;
    }

    @Override
    public void deleteOnefood(int memberIdx, int foodIdx) {
	favoriteFoodDAO.deleteOnefood(memberIdx, foodIdx);
    }

    @Override
    public void deleteMultiFood(int memberIdx, List<Integer> foodIdxList) {
	favoriteFoodDAO.deleteMultiFood(memberIdx, foodIdxList);
    }
}
