package com.example.appFireDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import services.JiraControllerService;
import services.JiraIssueConverter;
import services.JqlService;

@SpringBootApplication
public class AppFireDemoApplication {

	@Bean
	public JiraIssueConverter JiraIssueConverter() {
		return new JiraIssueConverter();
	}

	@Bean
	public JiraControllerService JiraControllerService() {
		return new JiraControllerService();
	}

	@Bean
	public JqlService JqlService() {
		return new JqlService();
	}

	public static void main(String[] args) {
		SpringApplication.run(AppFireDemoApplication.class, args);
	}

}
