package com.app.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.app.avro.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerComponent {

	@KafkaListener(topics = { "${app.topic.name}" }, groupId = "group_id")
	public void onMessage(ConsumerRecord<Integer, User> consumerRecord) {
		log.info("consumer-record={}", consumerRecord);
	}

}