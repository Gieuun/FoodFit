package com.sds.foodfit.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.model.member.MemberDetailService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SessionManager {

	@Autowired
	private MemberDetailService memberDetailService;

	public void setSession(HttpSession session, Member member) {
		// 세션 설정 로직
		MemberDetail memberDetail = memberDetailService.selectByMemberIdx(member.getMemberIdx());
		member.setMemberDetail(memberDetail);
		session.setAttribute("member", member);
		session.setAttribute("memberDetail", memberDetail);
	}

	public Model getSession(Model model, HttpSession session) {
		// 세션에서 로그인한 회원 정보 가져오기
		Member member = (Member) session.getAttribute("member");
		MemberDetail memberDetail = (MemberDetail) session.getAttribute("memberDetail");

		model.addAttribute("member", member);
		model.addAttribute("memberDetail", memberDetail);

		return model;
	}

}
