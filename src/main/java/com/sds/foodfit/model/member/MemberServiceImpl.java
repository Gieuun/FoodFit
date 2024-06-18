package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.domain.Sns;
import com.sds.foodfit.exception.MemberException;
import com.sds.foodfit.model.role.RoleDAO;
import com.sds.foodfit.model.sns.SnsDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private SnsDAO snsDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private MemberDAO memberDAO;

    @Transactional
    public void regist(Member member) throws MemberException {

	// SNS 정보 확인
	Sns sns = member.getSns();
	if (sns == null || sns.getSnsName() == null) {
	    sns = new Sns();
	    member.setSns(sns);
	    ;
	    throw new MemberException("SNS 정보가 null입니다.");
	}
	// SNS 정보 설정
	String snsName = sns.getSnsName();
	Sns selectedSns = snsDAO.selectByName(snsName);
	member.setSns(selectedSns);

	// Role 정보 설정 전에 null 체크
	if (member.getRole() == null || member.getRole().getRoleName() == null) {
	    log.debug("등록전 롤이 없네요 ");
	    throw new MemberException("Role 정보가 설정되지 않았습니다.");
	}
	Role role = roleDAO.selectByName(member.getRole().getRoleName());
	member.setRole(role); // role_idx가 채워진 DTO를 다시 MemberDTO 에 대입

	int result = memberDAO.insert(member);

	if (result < 1) {
	    log.debug("member insert 실패  ");
	    throw new MemberException("회원 등록 실패");
	}

	// 홈페이지만 추가 정보 처리
	if (sns.getSnsName().equals(sns.getSnsName())) {
	    log.debug("member result is" + result);
	    if (result < 1) {
		log.debug("member detail insert 실패  ");
		throw new MemberException("회원 추가정보 등록 실패");
	    }
	}
    }

    @Override
    public Member selectById(String id) {
	return memberDAO.selectById(id);
    }

}