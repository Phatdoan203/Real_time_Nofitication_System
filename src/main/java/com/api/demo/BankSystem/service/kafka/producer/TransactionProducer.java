package com.api.demo.BankSystem.service.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "transaction_topic";

    public void sendTransaction(String transactionJson) {
        kafkaTemplate.send(TOPIC, transactionJson);
    }
}
