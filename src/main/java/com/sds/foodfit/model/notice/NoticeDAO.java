package com.sds.foodfit.model.notice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Notice;

@Mapper
public interface NoticeDAO {
	public int getTotalCount(); //총 레코드 수
	public List<Notice> selectAll(Map<String, Integer> map); //모두 가져오기 
	public Notice select(int noticeIdx); //한건 가져오기 
	public int insert(Notice notice);//한건 넣기
	public int update(Notice notice); //한건 수정 
	public int delete(Notice notice); //한건 삭제   
	public int updateHit(int noticeIdx); //조회수 증가
	public List<Notice> searchNoticesByTitle(Map<String, Object> map); //검색기능
}
