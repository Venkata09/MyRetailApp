package com.myRetail.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @author vdokku
 */

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    String kafakTopic = "test-topic";

    public void sendData(String message) {
        kafkaTemplate.send(kafakTopic, message);
    }
}
