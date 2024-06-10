package com.sds.foodfit.model.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CalculateFoodService implements FoodDBService {

	@Autowired
	private FoodDBDAO foodDBDAO;

	@Override
	public Model setTableResult(String jsonData, Model model) {
		// 이거 유저 먹은거 넘버링 들어오는거

		double BMR;
		double TDEE;

		// 계산로직 짜고 나중에 model 분리
		// 생각을 해봤다. 개개인마다 BMR이 있을 것. 그러면 BMR도 테이블인가?(객체)
		// 그렇다면? memberDetail.height, memberDe.weight~

		// 날라온 키, 몸무게 <- 데이터에서 날라온다 (input단계에서 memberDetail 활용하고 여기서는 날아온 값만 이용하자)

		// ======json넣은 변수 넣어주면 됨.

		String gender = "female";
		int age = 20;
		double height = 160;
		double weight = 52;

		// =======================

		if (gender != "male") {
			BMR = 10 * weight + 6.25 * height - 5 * age - 161;
		} else {
			BMR = 10 * weight + 6.25 * height - 5 * age + 5;
		}

		TDEE = BMR * 1.55;

		int sumKcal = foodDBDAO.sumKcalByFoodIdx(null);

		return null;
	}

	@Override
	public Model setFoodResult(String jsonData, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

}
