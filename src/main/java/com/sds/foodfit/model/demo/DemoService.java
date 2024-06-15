package com.sds.foodfit.model.demo;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
	public int calculateBmr(double height, double weight, int age, boolean isMale) {
        if (isMale) {
            return (int) (66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age));
        } else {
            return (int) (655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age));
        }
    }

    public int calculateTdee(double bmr) {
        return (int) (bmr * 1.55); // assuming moderate activity level
    }
}
