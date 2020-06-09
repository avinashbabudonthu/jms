package com.avro;

import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import com.app.avro.model.User;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class Producer {

	@Test
	public void send100Messages() {
		IntStream.rangeClosed(1, 100).forEach(i -> send(i));
	}

	@Test
	public void sendMessages() {
		send(101);
	}

	public void send(int i) {

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
		properties.setProperty("acks", "1");
		properties.setProperty("retries", "0");
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
		properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

		KafkaProducer<String, User> kafkaProducer = new KafkaProducer<>(properties);
		String topic = "user-topic-avro-1";

		User user = User.newBuilder().setName("Amili").setAge(21).build();
		ProducerRecord<String, User> producerRecord = new ProducerRecord<>(topic, String.valueOf(i), user);

		kafkaProducer.send(producerRecord, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (null == exception) {
					System.out.println("success");
					System.out.println(metadata.toString());
				} else {
					exception.printStackTrace();
				}
			}
		});

		kafkaProducer.flush();
		kafkaProducer.close();

	}

}
