package com.api.demo.BankSystem.controller;

import com.api.demo.BankSystem.dto.TransactionDTO;
import com.api.demo.BankSystem.entity.Transaction;
import com.api.demo.BankSystem.service.kafka.producer.TransactionProducer;
import com.api.demo.BankSystem.service.redis.TransactionCacheService;
import com.api.demo.BankSystem.service.transaction.TransactionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionCacheService transactionCacheService;
    @Autowired
    private TransactionProducer transactionProducer;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody TransactionDTO transaction) {
        try {
            boolean existsInRedis = transactionCacheService.checkIfExistsInRedis(transaction.getTransactionId());
            if (existsInRedis) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Transaction already exists in Redis, cannot save to database.");
            }
            // Trong truong hop Redis mat data
            else {
                Transaction newTransaction = transactionService.saveTransaction(transaction);
                transactionCacheService.CacheTransaction(newTransaction);
                // Day data vao producer
                String transactionJson = new Gson().toJson(newTransaction);
                transactionProducer.sendTransaction(transactionJson);
                return ResponseEntity.status(HttpStatus.CREATED).body("Transaction saved successfully.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
