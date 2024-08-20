package com.example.transactionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegesterResponse {
    private Profile profile;
    private Wallet wallet;
    private String message;
}
