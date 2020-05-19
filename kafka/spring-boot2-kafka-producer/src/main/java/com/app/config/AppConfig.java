package com.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("localhost")
public class AppConfig {

	/*@Bean
	public NewTopic newTopic() {
		return TopicBuilder.name("students-topic").partitions(3).replicas(3).build();
	}*/
}