package com.app.config;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import com.app.avro.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppConfig {

	public void handleError(Exception e, Consumer<?, ?> consumer) {
		String s = e.getMessage().split("Error deserializing key/value for partition ")[1]
				.split(". If needed, please seek past the record to continue consumption.")[0];
		log.info("error-message={}", s); // practice-topic-2-0 at offset 8

		/*
		 * extract topic from above String s : practice-topic-2-0 at offset 8
		 * topic: practice-topic-2
		 */
		String topic = s.split(" ")[0];
		topic = topic.substring(0, topic.lastIndexOf("-"));
		log.info("topic={}", topic);

		/*
		 * extract offset from above String s : practice-topic-2-0 at offset 8
		 * partition: 0
		 */
		int partition = Integer.parseInt(s.split(" ")[0].substring(s.split(" ")[0].lastIndexOf("-") + 1));
		log.info("partition={}", partition);

		/*
		 * extract offset from above String s : practice-topic-2-0 at offset 8
		 * offset: 8
		 */
		int offset = Integer.valueOf(s.split("offset ")[1]);
		log.info("offset={}", offset);

		TopicPartition topicPartition = new TopicPartition(topic, partition);
		log.info("Skipping: {}-{} offset {}", topic, partition, offset);
		consumer.seek(topicPartition, offset + 1);
		log.info("Skipped: {}-{} offset {}", topic, partition, offset);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory(
			ConsumerFactory<String, User> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setErrorHandler(new ErrorHandler() {
			@Override
			public void handle(Exception e, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
					MessageListenerContainer container) {
				handleError(e, consumer);
			}

			@Override
			public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
				log.error("Exceptiion while consume message from topic");
			}

			@Override
			public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord, Consumer<?, ?> consumer) {
				handleError(e, consumer);
			}

		});
		return factory;
	}

}