package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.avro.model.User;
import com.app.kafka.AvroProducerComponent;
import com.app.kafka.StringProducerComponent;

@RestController
public class AppController {

	@Autowired
	private AvroProducerComponent producerComponent;

	@Autowired
	private StringProducerComponent stringProducerComponent;

	@PostMapping(value = "/avro/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> sendAvroMessageToKafkaTopic(@RequestBody User user) {
		producerComponent.sendMessage(user);
		return ResponseEntity.<Object>ok(user.getName());
	}

	@GetMapping(value = "/string/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> sendStringMessageToKafkaTopic() {
		stringProducerComponent.sendMessage();
		return ResponseEntity.<Object>ok("success");
	}
}
