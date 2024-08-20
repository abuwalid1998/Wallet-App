package com.example.transactionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@Document(collection = "TraSystem")
@NoArgsConstructor
public class Transaction {


    @Id
    String transactionId;
    String date;
    String description;
    String fromid;
    String toid;
    String amount;
    String status;
    String exchangerate;



}
