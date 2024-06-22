package com.sds.foodfit.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.Role;
import com.sds.foodfit.domain.Sns;
import com.sds.foodfit.exception.MemberException;
import com.sds.foodfit.model.role.RoleDAO;
import com.sds.foodfit.model.sns.SnsDAO;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
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
	private  HttpSession session;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public void regist(Member member) throws MemberException {
		
		// 아이디 중복 확인
        boolean isExists = isIdExists(member.getId());
        if (isExists) {
            log.debug("이미 등록된 아이디입니다: {}", member.getId());
            throw new MemberException("이미 등록된 아이디입니다.");
        }

		// SNS 정보 확인
		Sns sns = member.getSns();
		if (sns == null || sns.getSnsName() == null) {
			sns = new Sns();
			member.setSns(sns);
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
		

		// Member 삽입
		int result = memberDAO.insert(member);
		
		if(result <1) {
			throw new MemberException("회원  등록 실패");
		}

	}


	@Override
	public Member selectById(String id) {
		return memberDAO.selectById(id);
	}

	@Override
	public Member selectByMemberIdx(int memberIdx) {
		return memberDAO.selectByMemberIdx(memberIdx);
	}
	
	//아이디 중복 체크
	public boolean isIdExists(String id) {
	    int count = memberDAO.isIdExists(id);
	    return count > 0;
	}
	
	@Transactional
	//회원 정보 수정
	public void update(Member member) throws MemberException {
		 // 현재 로그인된 회원 정보 가져오기
	    Member loginUser = (Member) session.getAttribute("member");
	    log.debug("loginUser 정보는 " + loginUser);
	    
	    // 로그인 여부 확인
	    if (loginUser == null) {
	        throw new MemberException("로그인이 필요합니다");
	    }
	   
	     // 로그인한 회원의 회원 번호 가져오기
	     int memberIdx = loginUser.getMemberIdx();
	     if (memberIdx <= 0) {
	          throw new MemberException("유효하지 않은 회원 ID입니다: " + memberIdx);
	     }
	        
	      // 수정할 회원 정보 조회
	     Member dto = memberDAO.selectByMemberIdx(memberIdx);
	     log.debug("회원정보 조회 결과: " + dto);
	      
	     // 수정할 회원 정보가 없는 경우 예외 처리
	     if (dto == null) {
	         throw new MemberException("수정할 회원 정보를 찾을 수 없습니다");  
	     }
	     dto.setName(member.getName()); 
	     dto.setEmail(member.getEmail()); 
	     dto.setAge(member.getAge()); 
	     dto.setGender(member.getGender()); 
	     dto.setHeight(member.getHeight()); 
	     dto.setWeight(member.getWeight());
	     
	     try {
	    	 memberDAO.update(dto);
	    	 log.debug("회원 정보가 성공적으로 업데이트 되엇습니다.");
	     } catch(Exception e) {
	    	 throw new MemberException("회언 정보 업데이트 중 오류 발생");
	     }
	     
	}
	
	@Transactional
    // 아이디 변경
    public void updateId(int memberIdx, String newId) throws MemberException {
        // 새로운 아이디 유효성 검사
        if (!isValidId(newId)) {
            throw new MemberException("유효하지 않은 아이디입니다");
        }

        // 현재 로그인된 회원 정보 가져오기
        Member loginUser = (Member) session.getAttribute("member");
        log.debug("loginUser 정보는 " + loginUser);

        // 로그인 여부 확인
        if (loginUser == null) {
            throw new MemberException("로그인이 필요합니다");
        }

        // 로그인한 회원의 회원 번호 가져오기
        int currentMemberIdx = loginUser.getMemberIdx();
        if (currentMemberIdx != memberIdx) {
            throw new MemberException("변경 권한이 없는 회원입니다");
        }

        // 새로운 아이디로 회원 정보 업데이트
        loginUser.setId(newId);
        memberDAO.update(loginUser); // 데이터베이스에서 회원 정보 업데이트

        // 세션에 업데이트된 회원 정보 다시 설정
        session.setAttribute("member", loginUser);
    }
	
	//아이디 유효성 검사 메서드
	private boolean isValidId(String newId) {
		return true;
	}


	//회원 탈퇴 메서드
	@Transactional
    public void deleteMember(int memberIdx) throws MemberException {
        // 현재 로그인된 회원 정보 가져오기
        Member loginUser = (Member) session.getAttribute("member");
        log.debug("loginUser 정보는 " + loginUser);

        // 로그인 여부 확인
        if (loginUser == null) {
            throw new MemberException("로그인이 필요합니다");
        }

        // 로그인한 회원의 회원 번호 가져오기
        int currentMemberIdx = loginUser.getMemberIdx();
        if (currentMemberIdx != memberIdx) {
            throw new MemberException("탈퇴 권한이 없는 회원입니다");
        }

        // 회원 삭제
        memberDAO.deleteMember(memberIdx);

        // 세션에서 로그인 정보 제거
        session.removeAttribute("member");
    }

	//비밀번호 변경
	@Override
	public void updatePassword(int memberIdx, String currentPwd, String encodedNewPwd) throws MemberException {
		// 현재 로그인된 회원 정보 가져오기
	    Member loginUser = (Member) session.getAttribute("member");
	    log.debug("loginUser 정보는 {}", loginUser);

	    // 로그인 여부 확인
	    if (loginUser == null) {
	        throw new MemberException("로그인이 필요합니다");
	    }

	    // 로그인한 회원의 회원 번호 가져오기
	    int currentMemberIdx = loginUser.getMemberIdx();
	    if (currentMemberIdx != memberIdx) {
	        throw new MemberException("비밀번호 변경 권한이 없는 회원입니다");
	    }

	    // 새로운 비밀번호 설정
	    loginUser.setPwd(encodedNewPwd);

	    // 회원 정보 업데이트
	    memberDAO.update(loginUser);
	    log.debug("회원 정보 업데이트 완료");

	    // 세션에 업데이트된 회원 정보 다시 설정
	    session.setAttribute("member", loginUser);
	    log.debug("세션 정보 업데이트 완료");
	}

}