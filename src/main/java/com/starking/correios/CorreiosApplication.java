package com.starking.correios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories
public class CorreiosApplication {
	
	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		SpringApplication.run(CorreiosApplication.class, args);
	}
	
	public static  void close(int code) {
		SpringApplication.exit(ctx, ()-> code);
	}

}
