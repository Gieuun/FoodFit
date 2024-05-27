package com.sds.foodfit.sns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class NaverLogin {
	
	//사용자가 보게될 동의화면
		private String endpoint="https://nid.naver.com/oauth2.0/authorize";

		private String client_id="S9atPqdqU2K3oMtiEMEg";
		private String client_secret="amg2z4mEY6";
		
		//IDP 사업자로부터 응답받을 콜백주소
		private String redirect_uri="http://localhost:9876/recomember/sns/naver/callback";
		
		//콜백받을때 우리에게 전달될 파라미터명 
		private String response_type="code";
		private String state="naver_login_by_min";
		
		//토큰 요청 주소
		private String token_request_url="https://nid.naver.com/oauth2.0/token";
		private String grant_type="authorization_code";
		
		//회원정보 조회 주소  url 
		private String userinfo_url="https://openapi.naver.com/v1/nid/me";
		
		
		//로그인 요청 시 가져갈 파라미터 문자열 
		public String getGrantUrl() {
			StringBuilder sb = new StringBuilder();
			
			sb.append(endpoint+"?client_id="+client_id);
			sb.append("&redirect_uri="+redirect_uri);
			sb.append("&response_type="+response_type);
			sb.append("&state"+state);
			
			return sb.toString();
		}
}
