package com.example.transactionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {

    String fromWalletId;
    String toWalletId;
    String curcode;
    double amount;

}
