package com.kafka.spring.boot2.hello.world;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Producer {

    private static final String TOPIC_NAME="topic1";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(final String message){
        log.info("sending message={}", message);
        this.kafkaTemplate.send(TOPIC_NAME, message);
    }
}
