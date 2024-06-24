package com.sds.foodfit.model.food;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.sds.foodfit.domain.FoodDB;


@Service("calculateFoodService")
public class CalculateFoodService {

    @Autowired
    private FoodDBDAO foodDBDAO;

    public void calculateNutrition(String selectedFoods, float protein, float fat, float carbohydrate, float height, float weight, String gender, int age, Model model) {
        List<String> foodNames = List.of(selectedFoods.split(","))
                                      .stream()
                                      .map(String::trim)
                                      .map(name -> name.replace(" 삭제", ""))
                                      .collect(Collectors.toList());

        List<FoodDB> foods = foodNames.stream()
                .flatMap(name -> foodDBDAO.findByFoodName(name).stream())
                .collect(Collectors.toList());

        float totalProtein = (float) foods.stream().mapToDouble(FoodDB::getProtein).sum();
        float totalFat = (float) foods.stream().mapToDouble(FoodDB::getFat).sum();
        float totalCarbohydrate = (float) foods.stream().mapToDouble(FoodDB::getCarbohydrate).sum();
        float totalKcal = (float) foods.stream().mapToDouble(FoodDB::getKcal).sum();

        double bmr;
        if (gender.equals("male")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        int dailyCalories = (int) Math.round(bmr * 1.55);

        model.addAttribute("totalKcal", totalKcal);
        model.addAttribute("dailyCalories", dailyCalories);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalFat", totalFat);
        model.addAttribute("totalCarbohydrate", totalCarbohydrate);

        model.addAttribute("inputCarbohydrate", carbohydrate);
        model.addAttribute("inputProtein", protein);
        model.addAttribute("inputFat", fat);
    }
}
