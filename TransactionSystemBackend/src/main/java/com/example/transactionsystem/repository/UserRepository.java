package com.example.transactionsystem.repository;

import com.example.transactionsystem.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findUserByUsername(String email);
}
