package com.myRetail.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * @author vdokku
 */

@Service
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    String kafakTopic = "test-topic";

    public void sendData(String message) {
        kafkaTemplate.send(kafakTopic, message);
    }
}
