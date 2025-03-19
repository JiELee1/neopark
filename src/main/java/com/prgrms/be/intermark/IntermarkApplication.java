package com.prgrms.be.intermark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IntermarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntermarkApplication.class, args);
	}

}
