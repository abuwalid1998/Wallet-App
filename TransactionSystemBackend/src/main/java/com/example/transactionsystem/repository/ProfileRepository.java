package com.example.transactionsystem.repository;

import com.example.transactionsystem.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findByUserId(String userId);
}