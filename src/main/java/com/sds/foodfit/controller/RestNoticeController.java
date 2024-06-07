package com.sds.foodfit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.foodfit.model.notice.NoticeService;

@RestController
public class RestNoticeController {
	@Autowired
	private NoticeService noticeService;

	@GetMapping("/rest/notice/list")
	public List getList() {
		
		String url="jdbc:mysql://223.130.153.162:3306/food?characterEncoding=utf-8";
		
		Connection con=null;
		
		try {
			con = DriverManager.getConnection(url, "root", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("접속 결과는 "+con);
		
		return null;
	}
}
