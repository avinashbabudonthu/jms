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

@RestController
public class AppController {

	@Autowired
	private KafkaProducer appService;

	@PostMapping(value = "/v1/students", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Student saveStudent(@RequestBody Student student) {
		return student;
	}

	@GetMapping(value = "/v1/kafka-template", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return appService.getKafkaTemplate();
	}
}