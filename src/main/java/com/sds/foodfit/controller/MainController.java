package com.sds.foodfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
  	@GetMapping("/")
	public String getMain() {
		return "main/index";
		
	}
  	
  	@GetMapping("/demo")
	public String goDemo() {
		return "demo/input-form";
		
	}
}
