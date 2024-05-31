package com.sds.foodfit.model.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.foodfit.domain.Notice;
import com.sds.foodfit.exception.NoticeException;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public int getTotalCount() {
		return noticeDAO.getTotalCount();
	}

	@Override
	public List<Notice> selectAll(Map<String, Integer> map) {
		return noticeDAO.selectAll(map);
	}

	@Override
	public Notice select(int noticeIdx) {
		noticeDAO.updateHit(noticeIdx);
		Notice notice = noticeDAO.select(noticeIdx);
		return notice;
	}

	@Override
	public void insert(Notice notice) {
		int result = noticeDAO.insert(notice);
		if(result<1) {
			throw new NoticeException("글 등록 실패");
		}
	}

	@Override
	public void update(Notice notice) {
		int result = noticeDAO.update(notice);
		if(result<1) {
			throw new NoticeException("글 수정 실패");
		}
	}

	@Override
	public void delete(Notice notice) {
		int result = noticeDAO.delete(notice);
		if(result<1) {
			throw new NoticeException("글 삭제 실패");
		}
	}
	 @Override
	    public List<Notice> searchNoticesByTitle(Map<String, Object> map) {
	        return noticeDAO.searchNoticesByTitle(map);
	    }

}
