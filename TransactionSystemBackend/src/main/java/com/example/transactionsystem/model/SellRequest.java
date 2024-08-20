package com.example.transactionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellRequest {
    String walletid;
    double amount;
    String ledcode;
}
