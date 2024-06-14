package com.sds.foodfit.sns;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GoogleLogin {
	
	//사용자가 보게될 인증 화면 url
	private String endpoint ="https://accounts.google.com/o/oauth2/v2/auth";
	 
	//콜백 uri
	private String redirect_uri="http://localhost:9876/recomember/sns/google/callback";

	//콜백받을때 전달할 파라미터명 
	private String response_type="code";
	
	//사용자로부터 수집하고싶은 데이터 범위
	private String scope="https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

	//토큰 요청 주소
	private String token_request_url="https://oauth2.googleapis.com/token";
	private String grant_type="authorization_code";

	//회원정보 요청 url 
	private String userinfo_url="https://www.googleapis.com/oauth2/v1/userinfo";
	
	public String getGrantUrl() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(endpoint+"?client_id="+client_id);
		sb.append("&redirect_uri="+redirect_uri);
		sb.append("&response_type="+response_type);
		sb.append("&scope="+scope);
		
		return sb.toString();
	}
}
