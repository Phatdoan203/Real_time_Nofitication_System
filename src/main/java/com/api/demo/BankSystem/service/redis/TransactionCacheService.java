package com.api.demo.BankSystem.service.redis;

import com.api.demo.BankSystem.entity.Transaction;
import com.api.demo.BankSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class TransactionCacheService {
    private final String TRANSACTION_KEY_PREFIX = "transaction:";

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void CacheTransaction(Transaction transaction) {
        String key = TRANSACTION_KEY_PREFIX + transaction.getTransaction_id();
        redisTemplate.opsForValue().set(key, transaction);
    }

    /*
        Check xem id da ton tai trong db hoac redis hay chua:
            + Neu khong ton tai trong db thi luu vao trong db dong thoi luu trong redis
            + Neu da ton tai trong db ma chua ton tai trong redis thi luu vao trong redis
    */

    public boolean checkIfExistsInRedis(int transactionId) {
//        String redisKey = TRANSACTION_KEY_PREFIX + transactionId;
//
//        Object value = redisTemplate.opsForValue().get(redisKey);
//
//        if (Boolean.TRUE.equals(value)) {
//            return false; // Nếu tồn tại trong Redis, trả về false
//        }
//        return true; // Không tồn tại thì trả về true
        String redisKey = TRANSACTION_KEY_PREFIX + transactionId;

        // Kiểm tra xem key có tồn tại trong Redis hay không
        return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
    }


    public Transaction getTransactionFromRedis(int transaction_id) {
        String redisKey = TRANSACTION_KEY_PREFIX + transaction_id;
        return (Transaction) redisTemplate.opsForValue().get(redisKey);
    }
}
