package com.sds.foodfit.model.notice;

import java.util.List;
import java.util.Map;

import com.sds.foodfit.domain.Notice;

public interface NoticeService {
	public int getTotalCount();
	public List<Notice> selectAll(Map<String, Integer> map); //모두 가져오기 //리스트타입 선언
	public Notice select(int notice_idx); //한건 가져오기 
	public void insert(Notice notice);//한건 넣기
	public void update(Notice notice); //한건 수정 
	public void delete(Notice notice); //한건 삭제
	public List<Notice> searchNoticesByTitle(Map<String, Object> map); //검색기능
}
