package com.myRetail.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.app.model.Price;
import com.myRetail.app.repository.PriceRepository;
import com.myRetail.app.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vdokku
 */


@RestController
@RequestMapping(value = "/sendMessage/")
public class KafkaMessageController {

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/producer")
    public String producer(@RequestParam("mesage") String message) {
        kafkaSender.sendData(message);
        return "Message is Sent !!! ";
    }


    @RequestMapping("/product/{productId}")
    public ResponseEntity<Price> getPerson(@PathVariable String productId) throws JsonProcessingException {
        Price price = priceRepository.findPriceByProductId(productId);

        if (price != null) {
            kafkaTemplate.send("test-topic", objectMapper.writeValueAsString(price));
            return ResponseEntity.ok(price);
        } else {
            price = new Price("", "", "");
            kafkaTemplate.send("test-topic", objectMapper.writeValueAsString(price));
            return ResponseEntity.badRequest().body(null);
        }
    }
}
