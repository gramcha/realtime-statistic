package com.gramcha.realtimestatistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.gramcha.*"})
public class RealtimeStatisticApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeStatisticApplication.class, args);
	}
}
