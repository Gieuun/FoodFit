package com.sds.foodfit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.foodfit.model.member.CustomUserDetailsService;

@Configuration

public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
	return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
	return new RestTemplate();
    }


}
