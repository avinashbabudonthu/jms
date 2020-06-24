package com.app.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.app.avro.model.User;

@Component
public class AvroProducerComponent {

	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;

	@Value("${app.topic.name}")
	private String topic;

	public void sendMessage(User user) {
		String name = user.getName();
		int age = user.getAge();
		for (int i = 1; i <= 10; i++) {
			user.setName(name + i);
			user.setAge(age + i);
			ProducerRecord<String, User> producerRecord = new ProducerRecord<>(topic, String.valueOf(i), user);
			kafkaTemplate.send(producerRecord);
		}

	}

}
