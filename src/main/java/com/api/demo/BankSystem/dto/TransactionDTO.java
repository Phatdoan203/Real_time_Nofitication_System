package com.api.demo.BankSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    public int transactionId;
    public String status;
    public String type;
    public String from_account;
    public String to_account;
    public String account_holder;
    public String amount;
    public String currency;
    public String description;
}
