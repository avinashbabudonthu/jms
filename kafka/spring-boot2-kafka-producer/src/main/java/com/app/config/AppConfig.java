package com.app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("localhost")
public class AppConfig {

	@Bean
	public NewTopic newTopic() {
		return TopicBuilder.name("students-topic").partitions(3).replicas(3).build();
	}
}
