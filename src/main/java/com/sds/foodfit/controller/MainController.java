package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sds.foodfit.common.VisitorLogger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private VisitorLogger visitorLogger;

    @GetMapping("/")
    public String getMain(HttpServletRequest request, HttpServletResponse response) {

	// 작동테스트
	visitorLogger.countVisitor(request, response);

	log.debug("메인 호출");
	return "main/index";

    }

    @GetMapping("/demo")
    public String goDemo() {
	return "demo/input-form";
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

}
