package com.api.demo.BankSystem.service.kafka.consumer;

import com.api.demo.BankSystem.entity.Transaction;
import com.api.demo.BankSystem.service.telegram.TelegramBotService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class TransactionConsumer {
    @Autowired
    private TelegramBotService telegramBotService;

    @KafkaListener(topics = "transaction_topic", groupId = "transaction-group")
    public void consumeTransaction(String transactionJson) {
        Transaction transaction = new Gson().fromJson(transactionJson, Transaction.class);

        // Định dạng thời gian
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = sdf.format(transaction.getTime_stamp());

        String message = "💰 *GIAO DỊCH MỚI*\n"
                + "🆔 *Mã giao dịch*: " + transaction.getTransaction_id() + "\n"
                + "📅 *Thời gian*: " + formattedTime + "\n"
                + "📌 *Trạng thái*: " + transaction.getStatus() + "\n"
                + "🔄 *Loại giao dịch*: " + transaction.getType() + "\n"
                + "🏦 *Tài khoản gửi*: " + transaction.getFrom_account() + "\n"
                + "🏦 *Tài khoản nhận*: " + transaction.getTo_account() + "\n"
                + "👤 *Chủ tài khoản*: " + transaction.getAccount_holder() + "\n"
                + "💵 *Số tiền*: " + transaction.getAmount() + " " + transaction.getCurrency() + "\n"
                + "📝 *Nội dung*: " + transaction.getDescription();

        telegramBotService.sendMessage(message);
    }
}
