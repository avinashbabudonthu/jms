package com.app.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StringProducerComponent {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${app.topic.name}")
	private String topic;

	public void sendMessage() {
		for (int i = 1; i <= 10; i++) {
			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, String.valueOf(i),
					"Testing message-" + i);
			kafkaTemplate.send(producerRecord);
		}
	}
}
