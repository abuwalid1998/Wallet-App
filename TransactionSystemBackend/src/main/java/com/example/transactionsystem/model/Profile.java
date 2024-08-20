package com.example.transactionsystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;
    private String userId; // Reference to the user this profile belongs to
    private String fullName;
    private String email;
    private String phoneNumber;

}
