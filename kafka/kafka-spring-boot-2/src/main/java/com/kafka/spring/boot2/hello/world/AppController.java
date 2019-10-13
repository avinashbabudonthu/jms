package com.kafka.spring.boot2.hello.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    private Producer producer;

    @PostMapping("/send")
    public void sendMessage(@RequestParam("message")final String message){
        producer.sendMessage(message);
    }

}
