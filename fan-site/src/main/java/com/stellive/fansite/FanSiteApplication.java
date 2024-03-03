package com.stellive.fansite;

import com.stellive.fansite.service.YoutubeApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class FanSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanSiteApplication.class, args);
	}

	@Bean
	@Profile("test")
	public TestDataInit testDataInit(YoutubeApiService youtubeApiService) {
		return new TestDataInit(youtubeApiService);
	}
}