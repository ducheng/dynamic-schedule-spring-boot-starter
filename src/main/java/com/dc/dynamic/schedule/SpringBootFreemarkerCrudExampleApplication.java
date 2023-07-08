package com.dc.dynamic.schedule;

import com.dc.dynamic.schedule.annotation.EnableDynamicScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamicScheduling
public class SpringBootFreemarkerCrudExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFreemarkerCrudExampleApplication.class, args);
	}

}
