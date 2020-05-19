package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.app.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
		log.info("Message sent success, key={}, value={}, partition={}", key, value,
				sendResult.getRecordMetadata().partition());
	}

	@SneakyThrows
	public void handleFailure(Integer key, String value, Throwable throwable) {
		log.error("Error sending message", throwable);
		throw throwable;
	}

	@SneakyThrows
	public void sendStudentMessageAsync(Student student) {
		log.info("Send student={} to Kafka topic asynchronously", student);
		Integer key = student.getId();
		String value = objectMapper.writeValueAsString(student);
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> sendResult) {
				handleSuccess(key, value, sendResult);
			}

			@Override
			public void onFailure(Throwable throwable) {
				handleFailure(key, value, throwable);
			}
		});
	}

	@SneakyThrows
	public void sendStudentMessageSynchronous(Student student) {
		log.info("Send student={} to Kafka topic synchronously", student);
		Integer key = student.getId();
		String value = objectMapper.writeValueAsString(student);

		try {
			// sendDefault will take topic value from application.yml
			// SendResult<Integer, String> sendResult = kafkaTemplate.sendDefault(key, value).get();
			SendResult<Integer, String> sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS); // if result did not come in 1 second TimeoutException will be thrown
			log.info("Sent message to partition={}", sendResult.getRecordMetadata().partition());
		} catch (Exception e) {
			log.error("Exception while sending message", e);
			throw e;
		}
	}

	@SneakyThrows
	public void sendStudentMessageAsyncUsingTopicName(Student student) {
		log.info("Send student={} to Kafka topic asynchronously", student);
		Integer key = student.getId();
		String value = objectMapper.writeValueAsString(student);
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send("students-topic", key,
				value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> sendResult) {
				handleSuccess(key, value, sendResult);
			}

			@Override
			public void onFailure(Throwable throwable) {
				handleFailure(key, value, throwable);
			}
		});
	}

	@SneakyThrows
	public void sendStudentMessageAsyncUsingProducerRecord(Student student) {
		log.info("Send student={} to Kafka topic asynchronously", student);
		Integer key = student.getId();
		String value = objectMapper.writeValueAsString(student);

		ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>("students-topic", key, value);

		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> sendResult) {
				handleSuccess(key, value, sendResult);
			}

			@Override
			public void onFailure(Throwable throwable) {
				handleFailure(key, value, throwable);
			}
		});
	}

	@SneakyThrows
	public void sendStudentMessageAsyncUsingProducerRecordWithHeader(Student student) {
		log.info("Send student={} to Kafka topic asynchronously", student);
		Integer key = student.getId();
		String value = objectMapper.writeValueAsString(student);

		List<Header> recordHeaders = new ArrayList<>();
		Header header1 = new RecordHeader("source", "student-service".getBytes());
		recordHeaders.add(header1);
		ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>("students-topic", null, key, value,
				recordHeaders);

		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> sendResult) {
				handleSuccess(key, value, sendResult);
			}

			@Override
			public void onFailure(Throwable throwable) {
				handleFailure(key, value, throwable);
			}
		});
	}

}
