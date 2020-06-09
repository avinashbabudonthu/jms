package com.app.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.app.avro.model.User;

@Component
public class ProducerComponent {

	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;

	@Value("${app.topic.name}")
	private String topic;

	public void sendMessage(User user) {
		ProducerRecord<String, User> producerRecord = new ProducerRecord<>(topic, "1", user);
		kafkaTemplate.send(producerRecord);
	}
}
