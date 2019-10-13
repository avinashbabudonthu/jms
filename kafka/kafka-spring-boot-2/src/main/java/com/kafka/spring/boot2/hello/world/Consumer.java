package com.kafka.spring.boot2.hello.world;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Consumer {

    @KafkaListener(topics = "topic1", groupId = "topic1_id")
    public void consume(String message){
        log.info("received message={}", message);
    }
}
