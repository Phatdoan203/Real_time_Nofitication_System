package com.api.demo.BankSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(columnDefinition = "uniqueidentifier")
    @Id
    @Column(unique = true, nullable = false)
    private int transaction_id;
    @NotNull
    private Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
    @NotNull
    private String status;
    @NotNull
    private String type;
    @NotNull
    private String from_account;
    @NotNull
    private String to_account;
    @NotNull
    private String account_holder;
    @NotNull
    private String amount;
    @NotNull
    private String currency;
    @NotNull
    private String description;
}
