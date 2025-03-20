package com.api.demo.BankSystem.service.transaction;


import com.api.demo.BankSystem.dto.TransactionDTO;
import com.api.demo.BankSystem.entity.Transaction;
import com.api.demo.BankSystem.repository.TransactionRepository;
import com.api.demo.BankSystem.service.kafka.producer.TransactionProducer;
import com.api.demo.BankSystem.service.redis.TransactionCacheService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionCacheService transactionCacheService;
    @Autowired
    private TransactionProducer transactionProducer;
    public Transaction saveTransaction(TransactionDTO transaction) {
        try{
            Transaction newTransaction = new Transaction();
            newTransaction.setStatus(transaction.getStatus());
            newTransaction.setType(transaction.getType());
            newTransaction.setFrom_account(transaction.getFrom_account());
            newTransaction.setTo_account(transaction.getTo_account());
            newTransaction.setAccount_holder(transaction.getAccount_holder());
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setCurrency(transaction.getCurrency());
            newTransaction.setDescription(transaction.getDescription());
            Transaction savedTransaction = transactionRepository.save(newTransaction);
            // Luu data xuong redis
            transactionCacheService.CacheTransaction(newTransaction);
            // Day data vao producer
            String transactionJson = new Gson().toJson(newTransaction);
            transactionProducer.sendTransaction(transactionJson);
            // Luu data xuong db
            return savedTransaction;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
