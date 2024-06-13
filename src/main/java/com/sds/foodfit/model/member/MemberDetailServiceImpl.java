package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.MemberDetail;
import com.sds.foodfit.exception.MemberDetailException;

@Service
public class MemberDetailServiceImpl implements MemberDetailService {

	@Autowired
	private MemberDetailDAO memberDetailDAO;

	@Override
	public void insert(MemberDetail memberDetail) throws MemberDetailException {

		int result = memberDetailDAO.insert(memberDetail);

		if (result < 1) {
			throw new MemberDetailException("회원가입 실패");
		}

	}

	@Override
	public MemberDetail selectByMemberIdx(int memberIdx) {

		return memberDetailDAO.selectByMemberIdx(memberIdx);
	}

}
