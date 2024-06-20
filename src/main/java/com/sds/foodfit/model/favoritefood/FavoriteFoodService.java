
package com.sds.foodfit.model.favoritefood;

import java.util.List;

import com.sds.foodfit.domain.FavoriteFood;

public interface FavoriteFoodService {
    public boolean insertFavoriteFood(int memberIdx, int foodIdx); // 즐겨찾기 등록

    public List<FavoriteFood> selectByMemberIdx(int memberIdx); // 유저의 favoriteFood 객체 가져오기

    public void deleteOnefood(int memberIdx, int foodIdx); // 유저의 favoriteFood 중 1개의 foodDB 삭제하기

    public void deleteMultiFood(int memberIdx, List<Integer> foodIdxList); // 유저의 favoriteFood 중 1개의 foodDB 삭제하기

}
