package com.producer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Producer {

	@SneakyThrows
	@Test
	public void sendMessageWithoutKey() {
		final String topicName = "user-topic-avro-1";
		final Random random = new Random();

		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("value.serializer", StringSerializer.class.getName());

		try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties)) {
			ProducerRecord<String, String> record1 = new ProducerRecord<>(topicName, "record1=" + random.nextLong());

			Future<RecordMetadata> record1ResultFuture = producer.send(record1);
			RecordMetadata record1RecordMetadata = record1ResultFuture.get();

			log.info("checksum={}, offset={}, ", record1RecordMetadata.checksum(), record1RecordMetadata.offset());
			log.info("partition={}, serializedKeySize={}, serializedValueSize={}", record1RecordMetadata.partition(),
					record1RecordMetadata.serializedKeySize(), record1RecordMetadata.serializedValueSize());
			log.info("timestamp={}, topic={}", record1RecordMetadata.timestamp(), record1RecordMetadata.topic());
		}
	}

}