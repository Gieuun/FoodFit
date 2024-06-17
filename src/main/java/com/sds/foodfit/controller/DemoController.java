package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.foodfit.model.demo.DemoService;

@Controller
public class DemoController {
	
	@Autowired
	private DemoService calculatorService;

	@GetMapping("/demo/female-20s")
    public String femaleTwenties(@RequestParam("height") double height, @RequestParam("weight") double weight, Model model) {
        int age = 25;
        int bmr = calculatorService.calculateBmr(height, weight, age, false);
        int tdee = calculatorService.calculateTdee(bmr);
        model.addAttribute("bmr", bmr);
        model.addAttribute("tdee", tdee);
        return "demo/female-20s";
    }

    @GetMapping("/demo/female-30s")
    public String femaleThirties(@RequestParam("height") double height, @RequestParam("weight") double weight, Model model) {
        int age = 35;
        int bmr = calculatorService.calculateBmr(height, weight, age, false);
        int tdee = calculatorService.calculateTdee(bmr);
        model.addAttribute("bmr", bmr);
        model.addAttribute("tdee", tdee);
        return "demo/female-30s";
    }
    
    @GetMapping("/demo/female-40s")
    public String femaleForties(@RequestParam("height") double height, @RequestParam("weight") double weight, Model model) {
    	int age = 45;
    	int bmr = calculatorService.calculateBmr(height, weight, age, false);
    	int tdee = calculatorService.calculateTdee(bmr);
    	model.addAttribute("bmr", bmr);
    	model.addAttribute("tdee", tdee);
    	return "demo/female-40s";
    }

    @GetMapping("/demo/male-20s")
    public String maleTwenties(@RequestParam("height") double height, @RequestParam("weight") double weight, Model model) {
        int age = 25;
        int bmr = calculatorService.calculateBmr(height, weight, age, true);
        int tdee = calculatorService.calculateTdee(bmr);
        model.addAttribute("bmr", bmr);
        model.addAttribute("tdee", tdee);
        return "demo/male-20s";
    }

    @GetMapping("/demo/male-30s")
    public String maleThirties(@RequestParam("height") double height, @RequestParam("weight") double weight, Model model) {
        int age = 35;
        int bmr = calculatorService.calculateBmr(height, weight, age, true);
        int tdee = calculatorService.calculateTdee(bmr);
        model.addAttribute("bmr", bmr);
        model.addAttribute("tdee", tdee);
        return "demo/male-30s";
    }
    @GetMapping("/demo/male-40s")
    public String maleForties(@RequestParam("height") double height, @RequestParam("weight") double weight, Model model) {
    	int age = 45;
    	int bmr = calculatorService.calculateBmr(height, weight, age, true);
    	int tdee = calculatorService.calculateTdee(bmr);
    	model.addAttribute("bmr", bmr);
    	model.addAttribute("tdee", tdee);
    	return "demo/male-40s";
    }
}