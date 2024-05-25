package com.sds.foodfit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.foodfit.common.Pager;
import com.sds.foodfit.domain.Notice;
import com.sds.foodfit.exception.NoticeException;
import com.sds.foodfit.model.notice.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private Pager pager;
	
	//주의할점: 스프링 부트에서는 전송되어 오는 매개변수 DTO에 소속된 변수가 아닌 단독 변수가 매개변수를 받을 경우
	//특히 int형으로 받고 싶을 경우, 그냥 받으면 에러..
	//파라미터에 대한 디폴트값을 명시
	@GetMapping("/notice/list")
	public String getList(Model model, @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		//레코드를 가져올때 모두 한꺼번에 가져오지 말고 현재 페이지에서 볼 레코드만 골라서 가져오자
		//메모리 효율성이 극대화
		
		pager.init(noticeService.getTotalCount(), currentPage);
		
		Map map = new HashMap(); //n부터 ~m개까지의 레코드를 가져오기 위한 매개변수를 모아놓을 맵
		map.put("startIndex", pager.getStartIndex());
		map.put("rowCount", pager.getPageSize());
		
		List noticeList = noticeService.selectAll(map); //3단계: 일시키기 
		model.addAttribute("noticeList", noticeList); //4단계: 결과 저장 
		model.addAttribute("pager", pager);
		
		return "notice/list";    
	}
	
	@GetMapping("/notice/writeform")
	public String getRegistForm() {
		
		return "notice/write";
	}
	
	//글쓰기 요청 처리 
	@PostMapping("/notice/regist")
	public ModelAndView regist(Notice notice) {
		System.out.println("글쓰기 요청");
		noticeService.insert(notice); //3단계: 일 시키기 
		
		ModelAndView mav = new ModelAndView("redirect:/notice/list");
		
		return mav;
	}
	
	//상세보기 요청 처리
	@GetMapping("/notice/detail")
	public String getDetail(Model model,@RequestParam(value="notice_idx", defaultValue="0") int notice_idx) {
		Notice notice = noticeService.select(notice_idx);
		model.addAttribute("notice", notice);
		return "notice/content";
	}
	
	@PostMapping("/notice/edit")
	public String edit(Notice notice) {

		noticeService.update(notice);
		return "redirect:/notice/detail?notice_idx="+notice.getNotice_idx();
	}
	
	@PostMapping("/notice/del")
	public String del(Notice notice) {
		noticeService.delete(notice);
		return "redirect:/notice/list";
	}
	
	//컨트롤러가 보유한 메서드들 중 예외가 발생하는 메서드가 있다면 해당 메서드의 실행부는 해당 라인에서 
	//곧바로 아래의 메서드로 진입하게 됨..
	@ExceptionHandler(NoticeException.class)
	public ModelAndView handle(NoticeException e) {
		//에러 원인 보유한 e 를 가지고, 에러 페이지로 가자 
		ModelAndView mav = new ModelAndView("error/result");
		mav.addObject("e", e);
		return mav;
	}
}
