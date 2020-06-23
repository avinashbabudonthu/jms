package com.app.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
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

	/*@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory(
			ConsumerFactory<String, User> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.getContainerProperties().setAckOnError(false);
		factory.getContainerProperties().setAckMode(AckMode.RECORD);
		Properties properties = new Properties();
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
		factory.getContainerProperties().setKafkaConsumerProperties(properties);
		// factory.setErrorHandler(new SeekToCurrentErrorHandler());
		return factory;
	}*/

	/*@Bean
	public ConsumerFactory<String, User> kafkaConsumerFactory(KafkaProperties properties,
			JsonDeserializer customDeserializer) {
	
		return new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties(), customDeserializer,
				customDeserializer);
	}*/

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory(
			ConsumerFactory<String, User> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setErrorHandler(new ErrorHandler() {
			@Override
			public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
					MessageListenerContainer container) {
				String s = thrownException.getMessage().split("Error deserializing key/value for partition ")[1]
						.split(". If needed, please seek past the record to continue consumption.")[0];
				System.out.println(s);
				String topics = s.split(" ")[0];
				topics = topics.substring(0, topics.lastIndexOf("-"));
				int offset = Integer.valueOf(s.split("offset ")[1]);

				// practice-topic-2-0 at offset 8
				// int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);
				int partition = Integer.parseInt(s.split(" ")[0].substring(s.split(" ")[0].lastIndexOf("-") + 1));

				// practice-topic-2-0-0
				TopicPartition topicPartition = new TopicPartition(topics, partition);
				//log.info("Skipping " + topic + "-" + partition + " offset " + offset);
				consumer.seek(topicPartition, offset + 1);
				System.out.println("OKKKKK");
			}

			@Override
			public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {

			}

			@Override
			public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord, Consumer<?, ?> consumer) {
				String s = e.getMessage().split("Error deserializing key/value for partition ")[1]
						.split(". If needed, please seek past the record to continue consumption.")[0];
				String topics = s.split("-")[0];
				int offset = Integer.valueOf(s.split("offset ")[1]);
				int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);

				TopicPartition topicPartition = new TopicPartition(topics, partition);
				//log.info("Skipping " + topic + "-" + partition + " offset " + offset);
				consumer.seek(topicPartition, offset + 1);
				System.out.println("OKKKKK");

			}
		});

		return factory;
	}

}