package com.sds.foodfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
    @GetMapping("/")
    public String getMain() {

	// 네이버 클라우드서버 이관 시 작동테스트
	/*
	 * VisitorLogger visitorLogger = new VisitorLogger();
	 * visitorLogger.countVisitor();
	 */
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
