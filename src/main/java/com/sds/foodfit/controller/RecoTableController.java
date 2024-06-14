package com.sds.foodfit.controller;

import com.sds.foodfit.domain.FoodDB;
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

    @Autowired
    public RecoTableController(@Qualifier("tableServiceImpl") FoodDBService foodDBService) {
        this.foodDBService = foodDBService;
    }

    @GetMapping("/insert")
    public String showInsertPage(Model model) {
        return "recotable/insert";
    }

    @GetMapping("/foodpopup")
    public String showFoodPopup() {
        return "recotable/foodpopup";
    }

    @GetMapping("/api/foods")
    @ResponseBody
    public List<FoodDB> getAllFoods(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return foodDBService.searchFoodsByName(search);
        } else {
            return foodDBService.getAllFoods();
        }
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("height") int height,
                            @RequestParam("weight") int weight,
                            @RequestParam("gender") String gender,
                            @RequestParam("age") int age,
                            Model model) {
        double bmr;
        if (gender.equals("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        int dailyCalories = (int) Math.round(bmr * 1.55);

        model.addAttribute("dailyCalories", dailyCalories);

        return "recotable/result";
    }
}
