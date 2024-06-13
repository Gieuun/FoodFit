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

	@Autowired
	private MemberDetailDAO memberDetailDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void regist(MemberDetail memberDetail) throws MemberException {

		// SNS 정보 확인
		Sns sns = memberDetail.getMember().getSns();
		if (sns == null || sns.getSnsName() == null) {
			throw new MemberException("SNS 정보가 null입니다.");
		}
		// SNS 정보 설정
		String snsName = sns.getSnsName();
		Sns selectedSns = snsDAO.selectByName(snsName);
		memberDetail.getMember().setSns(selectedSns);

		// Role 정보 설정 전에 null 체크
		if (memberDetail.getMember().getRole() == null || memberDetail.getMember().getRole().getRoleName() == null) {
			log.debug("등록전 롤이 없네요 ");
			throw new MemberException("Role 정보가 설정되지 않았습니다.");
		}
		Role role = roleDAO.selectByName(memberDetail.getMember().getRole().getRoleName());
		memberDetail.getMember().setRole(role); // role_idx가 채워진 DTO를 다시 MemberDTO 에 대입

		String hashedPassword = passwordEncoder.encode(memberDetail.getMember().getPwd());
		memberDetail.getMember().setPwd(hashedPassword);

		int result = memberDAO.insert(memberDetail.getMember());

		if (result < 1) {
			log.debug("member insert 실패  ");
			throw new MemberException("회원 등록 실패");
		}

		// 홈페이지만 추가 정보 처리
		if (sns.getSnsName().equals("homepage")) {

			log.debug("member result is" + result);

			// memberDetail 삽입
			memberDetailDAO.insert(memberDetail);
			if (result < 1) {
				log.debug("member detail insert 실패  ");
				throw new MemberException("회원 추가정보 등록 실패");
			}
		}
	}

	public Member selectById(String id) {
		return memberDAO.selectById(id);
	}

}