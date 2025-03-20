package com.api.demo.BankSystem.service.redis;

import com.api.demo.BankSystem.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class TransactionCacheService {
    private final String TRANSACTION_KEY_PREFIX = "transaction:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void CacheTransaction(Transaction transaction) {
        String key = TRANSACTION_KEY_PREFIX + transaction.getTransaction_id().toString();
        redisTemplate.opsForValue().set(key, transaction, Duration.ofMinutes(10));
    }

    // Lấy giao dịch từ Redis bằng UUID
    public Transaction getTransactionById(UUID transactionId) {
        String key = TRANSACTION_KEY_PREFIX + transactionId.toString();
        return (Transaction) redisTemplate.opsForValue().get(key);
    }
}
