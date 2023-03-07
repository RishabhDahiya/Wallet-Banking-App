package com.nextuple.walletapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    private String transactionType;
    private String username;
    private String debitedFrom;
    private String creditedTo;
    private int amount;
}
