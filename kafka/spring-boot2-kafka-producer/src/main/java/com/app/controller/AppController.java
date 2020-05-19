package com.app.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Student;
import com.app.service.KafkaProducer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AppController {

	@Autowired
	private KafkaProducer kafkaProducer;

	@SneakyThrows
	@PostMapping(value = "/v1/students", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Student saveStudent(@RequestBody Student student) {
		log.info("Before Send student, student={}", student);
		kafkaProducer.sendStudentMessageAsync(student);
		log.info("After Send student, student={}", student);
		return student;
	}

	@SneakyThrows
	@PostMapping(value = "/v2/students", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Student sendStudentMessageSynchronous(@RequestBody Student student) {
		log.info("Before Send student, student={}", student);
		kafkaProducer.sendStudentMessageSynchronous(student);
		log.info("After Send student, student={}", student);
		return student;
	}

	@SneakyThrows
	@PostMapping(value = "/v3/students", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Student sendStudentMessageAsync_Solution2(@RequestBody Student student) {
		log.info("Before Send student, student={}", student);
		kafkaProducer.sendStudentMessageAsyncUsingTopicName(student);
		log.info("After Send student, student={}", student);
		return student;
	}

	@SneakyThrows
	@PostMapping(value = "/v4/students", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Student sendStudentMessageAsyncProducerRecord(@RequestBody Student student) {
		log.info("Before Send student, student={}", student);
		kafkaProducer.sendStudentMessageAsyncUsingProducerRecord(student);
		log.info("After Send student, student={}", student);
		return student;
	}

	@SneakyThrows
	@PostMapping(value = "/v5/students", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Student sendStudentMessageAsyncUsingProducerRecordWithHeader(@RequestBody Student student) {
		log.info("Before Send student, student={}", student);
		kafkaProducer.sendStudentMessageAsyncUsingProducerRecordWithHeader(student);
		log.info("After Send student, student={}", student);
		return student;
	}

	@GetMapping(value = "/v1/kafka-template", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return kafkaProducer.getKafkaTemplate();
	}
}