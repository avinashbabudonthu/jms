package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;

	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

}
