package com.sds.foodfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
	
	@GetMapping("/demo/female-20s")
	public String famaleTwenties() {
		return "demo/female-20s";
	}
	@GetMapping("/demo/female-30s")
	public String femaleThirties() {
		return "demo/female-30s";
	}
	@GetMapping("/demo/male-20s")
	public String maleTwenties() {
		return "demo/male-20s";
	}
	@GetMapping("/demo/male-30s")
	public String maleThirties() {
		return "demo/male-30s";
	}
}
