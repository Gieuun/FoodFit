package com.sds.foodfit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration

public class AppConfig {

	@Bean
	public String key() {
		return "b7819c165e724c0ab5cd";
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
