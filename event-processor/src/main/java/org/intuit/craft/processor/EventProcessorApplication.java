package org.intuit.craft.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "org.intuit.craft")
@EnableJpaRepositories(basePackages = "org.intuit.craft")
@EnableScheduling
public class EventProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventProcessorApplication.class, args);
	}

}
