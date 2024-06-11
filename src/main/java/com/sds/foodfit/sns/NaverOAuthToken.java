package com.sds.foodfit.sns;

import lombok.Data;

@Data
public class NaverOAuthToken {
	
	// 네이버가 제공하는 액세스 토큰
	private String access_token;
	
	// 네이버가 제공하는 리프레시 토큰
	private String refresh_token;
	
	// 토큰 타입
	private String token_type;
	
	// 액세스 토큰 만료 시간
	private String expires_in;
}
