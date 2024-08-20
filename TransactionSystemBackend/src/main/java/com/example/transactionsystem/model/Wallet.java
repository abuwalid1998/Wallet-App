package com.example.transactionsystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;


@Data
@Document(collection = "wallets")
public class Wallet {
    @Id
    private String id;
    private String userId; // Reference to the user who owns this wallet
    private Map<String, Ledger> ledgers; // A map of ledgers by their ID

    // Getters and setters
}

