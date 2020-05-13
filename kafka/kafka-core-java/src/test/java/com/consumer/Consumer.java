package com.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer {

	@Test
	public void consumeByTopicBySubcribe() {
		final String topicName = "topic1";

		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.deserializer", StringDeserializer.class.getName());
		properties.put("value.deserializer", StringDeserializer.class.getName());
		properties.put("group.id", "group1");

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties)) {
			consumer.subscribe(Arrays.asList(topicName));
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(1000 * 5);
				for (ConsumerRecord<String, String> record : records) {
					log.info("Topic={}, partition={}, offset={}, key={}, value={}", record.topic(), record.partition(),
							record.offset(), record.key(), record.value());
				}
			}
		}
	}

}