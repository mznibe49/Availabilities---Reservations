package com.giskard.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GiskardTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiskardTestApplication.class, args);
	}

}
