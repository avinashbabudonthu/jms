package com.app.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	/*@Bean
	public ConcurrentKafkaListenerContainerFactory<String, GenericRecord> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, GenericRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// factory.setConsumerFactory(consumerFac);
		factory.setConsumerFactory(consumerFactory);
		factory.setErrorHandler(new SeekToCurrentErrorHandler());
		return factory;
	}*/

}