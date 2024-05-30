package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.domain.Sns;
import com.sds.foodfit.exception.MemberException;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private SnsDAO snsDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MemberDetailDAO memberDetailDAO;
	

	@Autowired
	private	PasswordEncoder passwordEncoder;
	
	@Autowired
	private HttpSession session;
	

	@Transactional
	public void regist(Member member) throws MemberException {
		Sns sns = snsDAO.selectByName(member.getSns().getSns_name());
		member.setSns(sns); //sns_idx가 채워진 DTO를 다시 MemberDTO 에 대입
		
		Role role = roleDAO.selectByName(member.getRole().getRole_name());
		member.setRole(role); //role_idx가 채워진 DTO를 다시 MemberDTO 에 대입
		
		int result = memberDAO.insert(member);
		
		if(result <1) {
			throw new MemberException("회원 등록 실패");
		}
		
		if(sns.getSns_name().equals("homepage")) {
			MemberDetail memberDetail = member.getMemberDetail();
			memberDetail.setMember(member);
			
			log.debug("member result is"+result);
			
			member.setPwd(passwordEncoder.encode(member.getPwd()));
			
			result = memberDetailDAO.insert(memberDetail);
			if(result <1){
				throw new MemberException("회원 추가정보 등록 실패");
			}
		}
	}
	public Member selectByid(String id) {
		return memberDAO.selectByid(id);
	}

}
