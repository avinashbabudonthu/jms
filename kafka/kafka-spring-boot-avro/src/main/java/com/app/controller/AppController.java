package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.avro.model.User;
import com.app.kafka.ProducerComponent;

@RestController
public class AppController {

	@Autowired
	private ProducerComponent producerComponent;

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> sendMessageToKafkaTopic(@RequestBody User user) {
		producerComponent.sendMessage(user);
		return ResponseEntity.<Object>ok(user.getName());
	}
}