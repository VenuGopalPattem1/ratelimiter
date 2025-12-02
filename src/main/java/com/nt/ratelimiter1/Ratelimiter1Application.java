package com.nt.ratelimiter1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// @ConfigurationPropertiesScan(basePackages = "com.nt.ratelimiter1.config")
@SpringBootApplication
public class Ratelimiter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ratelimiter1Application.class, args);
	}

}
