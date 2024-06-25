package com.sds.foodfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FoodFitApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FoodFitApplication.class, args);
    }
}
