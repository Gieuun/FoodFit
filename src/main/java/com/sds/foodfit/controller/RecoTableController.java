package com.sds.foodfit.controller;

import com.sds.foodfit.domain.FoodDB;
import com.sds.foodfit.model.food.CalculateFoodService;
import com.sds.foodfit.model.food.FoodDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RecoTableController {

    private final FoodDBService foodDBService;
    private final CalculateFoodService calculateFoodService;

    @Autowired
    public RecoTableController(@Qualifier("tableServiceImpl") FoodDBService foodDBService,
                               @Qualifier("calculateFoodService") CalculateFoodService calculateFoodService) {
        this.foodDBService = foodDBService;
        this.calculateFoodService = calculateFoodService;
    }

    @GetMapping("recotable/insert")
    public String showInsertPage(Model model) {
        return "recotable/insert";
    }

    @GetMapping("recotable/foodpopup")
    public String showFoodPopup() {
        return "recotable/foodpopup";
    }

    @GetMapping("recotable/api/foods")
    @ResponseBody
    public List<FoodDB> getAllFoods(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return foodDBService.searchFoodsByName(search);
        } else {
            return foodDBService.getAllFoods();
        }
    }

    @PostMapping("recotable/calculate")
    public String calculate(@RequestParam("selectedFoods") String selectedFoods,
                            @RequestParam("protein") float protein,
                            @RequestParam("fat") float fat,
                            @RequestParam("carbohydrate") float carbohydrate,
                            @RequestParam("height") float height,
                            @RequestParam("weight") float weight,
                            @RequestParam("gender") String gender,
                            @RequestParam("age") int age,
                            Model model) {

        calculateFoodService.calculateNutrition(selectedFoods, protein, fat, carbohydrate, height, weight, gender, age, model);
        return "recotable/result";
    }
}
