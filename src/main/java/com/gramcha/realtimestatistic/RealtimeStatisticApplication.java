package com.gramcha.realtimestatistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.gramcha.*"})
public class RealtimeStatisticApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeStatisticApplication.class, args);
	}
}
