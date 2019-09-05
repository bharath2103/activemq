package com.example.queuebrowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.example.queuebrowser"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class QueuebrowserApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueuebrowserApplication.class, args);
	}

}
