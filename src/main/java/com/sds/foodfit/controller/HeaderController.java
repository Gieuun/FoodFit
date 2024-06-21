package com.sds.foodfit.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sds.foodfit.domain.CustomUserDetails;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HeaderController {

    @GetMapping("/headerinfo")
    public String headerinfo(Model model, HttpSession session) {
        // 현재 인증된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 세션에서 필요한 정보 가져오기
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;

                // 세션에서 가져온 정보를 모델에 추가하여 헤더에서 사용할 수 있도록 함
                model.addAttribute("favoriteFood", userDetails.getFavoriteFood());
                model.addAttribute("gender", userDetails.getMember().getGender());
                model.addAttribute("age", userDetails.getMember().getAge());
                model.addAttribute("height", userDetails.getMember().getHeight());
                model.addAttribute("weight", userDetails.getMember().getWeight());

		// 로그로 값들을 확인
		log.debug("===== favoriteFood: ", userDetails.getMember().getGender());
		log.debug("===== gender: ", userDetails.getMember().getGender());
		log.debug("===== age: ", userDetails.getMember().getAge());
		log.debug("===== height: ", userDetails.getMember().getHeight());
		log.debug("===== weight: ", userDetails.getMember().getWeight());
	    }
	}

	return "fragments/headerinfo"; // fragments 디렉토리에 있는 Thymeleaf 템플릿의 이름 반환
    }

}
