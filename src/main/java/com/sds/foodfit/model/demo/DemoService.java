package com.sds.foodfit.model.demo;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
	public int calculateBmr(double height, double weight, int age, boolean isMale) {
        if (isMale) {
            return (int) (10 * weight + 6.25 * height - 5 * age + 5);
        } else {
            return (int) (10 * weight + 6.25 * height - 5 * age - 161);
        }
    }

    public int calculateTdee(double bmr) {
        return (int) (bmr * 1.55); // assuming moderate activity level
    }
}
