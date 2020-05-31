package com.app.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

	@KafkaListener(topics = { "employee-topic-1" })
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
		log.info("consumer-record={}", consumerRecord);
	}
}