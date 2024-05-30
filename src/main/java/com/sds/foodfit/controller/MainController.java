package com.sds.foodfit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
		return "demo/input_form";
	}

	@GetMapping("/recotable")
	public String goRecoTable() {
		return "recotable/insert";

	}

	@GetMapping("/recofood")
	public String goRecoFood() {
		return "recofood/insert";

	}
	
	@GetMapping("/login")
	public String goLogin() {
		return "/recomember/login";

	}
	
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String goAdmin() {
        return "redirect:/admin/updates";
    }

}
