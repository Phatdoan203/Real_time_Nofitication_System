package com.api.demo.BankSystem.service.transaction;


import com.api.demo.BankSystem.dto.TransactionDTO;
import com.api.demo.BankSystem.entity.Transaction;
import com.api.demo.BankSystem.repository.TransactionRepository;
import com.api.demo.BankSystem.service.kafka.producer.TransactionProducer;
import com.api.demo.BankSystem.service.redis.TransactionCacheService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionCacheService transactionCacheService;
    @Autowired
    private TransactionProducer transactionProducer;

    public Transaction saveTransaction(TransactionDTO transaction) {
        try{
            Transaction newTransaction = new Transaction();
            newTransaction.setTransaction_id(transaction.getTransactionId());
            newTransaction.setStatus(transaction.getStatus());
            newTransaction.setType(transaction.getType());
            newTransaction.setFrom_account(transaction.getFrom_account());
            newTransaction.setTo_account(transaction.getTo_account());
            newTransaction.setAccount_holder(transaction.getAccount_holder());
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setCurrency(transaction.getCurrency());
            newTransaction.setDescription(transaction.getDescription());
            Transaction savedTransaction = transactionRepository.save(newTransaction);


//            boolean checkTransactionInDB = existTransaction(savedTransaction.getTransaction_id());
            // Kiem tra db
//            transactionCacheService.CacheTransaction(savedTransaction);
            return savedTransaction;
        } catch (RuntimeException e) {
            return null;
        }
    }

    public boolean existTransaction(int transaction_id) {
        transactionRepository.findById(transaction_id);
        return true;
    }

}
