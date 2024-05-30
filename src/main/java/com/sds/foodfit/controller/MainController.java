package com.sds.foodfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
	
	@GetMapping("/demo/demoresult")
	public String goDemoResult() {
		return "demo/demoResult";
	}

	@GetMapping("/recotable")
	public String goRecoTable() {
		return "recotable/insert";

	}

	@GetMapping("/recofood")
	public String goRecoFood() {
		return "recofood/insert";

	}
}
  		

