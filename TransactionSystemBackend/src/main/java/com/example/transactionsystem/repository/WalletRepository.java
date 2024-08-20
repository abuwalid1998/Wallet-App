package com.example.transactionsystem.repository;

import com.example.transactionsystem.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    Wallet findByUserId(String userId);
}