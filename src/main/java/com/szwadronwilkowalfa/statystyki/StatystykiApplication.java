package com.szwadronwilkowalfa.statystyki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCaching
public class StatystykiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StatystykiApplication.class, args);
	}

}