package com.sds.foodfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeController {

	@GetMapping("/notice/list")
	public String getlist() {
		
		return "notice/list";
	}
}
