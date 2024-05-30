package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.exception.MemberDetailException;

@Service
public class MemberDetailServiceImpl implements MemberDetailService {
	
	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private MemberDetailDAO memberDetailDAO;
	
	@Override
	public void insert(MemberDetail memberDetail) throws MemberDetailException {
		
		int result = memberDetailDAO.insert(memberDetail);
		
		if(result <1) {
			throw new MemberDetailException("회원가입 실패");
		}
		
	}

	@Override
	public Member selectByIdx(int member_idx) {
		return memberDetailDAO.selectByIdx(member_idx);
	}

}
