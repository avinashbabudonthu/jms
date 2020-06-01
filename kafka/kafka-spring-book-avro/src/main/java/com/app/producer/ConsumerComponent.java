package com.app.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.app.avro.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerComponent {

	@KafkaListener(topics = { "user-topic-avro-1" }, groupId = "group_id")
	public void onMessage(ConsumerRecord<Integer, User> consumerRecord) {
		log.info("consumer-record={}", consumerRecord);
	}
}