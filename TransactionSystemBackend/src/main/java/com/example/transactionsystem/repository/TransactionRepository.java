package com.example.transactionsystem.repository;

import com.example.transactionsystem.model.Profile;
import com.example.transactionsystem.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findAllByFromidOrToid(String fromid, String toid);
}
