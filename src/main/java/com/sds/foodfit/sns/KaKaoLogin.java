package com.sds.foodfit.sns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class KaKaoLogin {
	
	// 웹사이트 이용자가 보게될 동의화면 주소
		private String endpoint="https://kauth.kakao.com/oauth/authorize";
		
		private String client_id="970cb56fa228e863ca1929adc17cd021";
		
		//콜백 받을때 전달할 파라미터명
		private String redirect_uri="http://localhost:9876/recomember/sns/kakao/callback";
		private String response_type="code";
		
		//토큰 요청 주소
		private String token_request_url="https://kauth.kakao.com/oauth/token";
		private String grant_type="authorization_code";
		
		//회원정보 요청 url
		private String userinfo_url="https://kapi.kakao.com/v2/user/me";
		
		
		//로그인 요청 시 가져갈 파라미터 문자열 
		public String getGrantUrl() {
			StringBuilder sb = new StringBuilder();
			
			sb.append(endpoint+"?client_id="+client_id);
			sb.append("&redirect_uri="+redirect_uri);
			sb.append("&response_type="+response_type);
			
			return sb.toString();
		}
}
