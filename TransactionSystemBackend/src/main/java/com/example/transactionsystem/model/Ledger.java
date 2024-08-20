package com.example.transactionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ledger {
    private String id; // Ledger ID (e.g., "100" for Ethereum)
    private String currency; // Currency type (e.g., "ETH", "BTC", "USD")
    private double balance; // Current balance in the ledger

    // Getters and setters
}
