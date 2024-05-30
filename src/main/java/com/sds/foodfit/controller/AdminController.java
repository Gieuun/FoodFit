package com.sds.foodfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.model.admin.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	//관리자 로그인 요청처리
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping("/admin/update")
		public ModelAndView adminLogin() {
			ModelAndView mav = new ModelAndView("admin/loadapi");
			
			Member admin = adminService.findAdmin();
			
			
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			mav.addObject("authentication", authentication);
			log.debug("모델엔뷰"+mav);
			return mav;
		}
		
		@GetMapping("/403")
	    public String accessDenied() {
	        return "403"; // 권한이 없을 때 표시할 페이지
	    }

}
