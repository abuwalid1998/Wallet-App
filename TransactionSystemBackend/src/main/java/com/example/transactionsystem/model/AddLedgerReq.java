package com.example.transactionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class AddLedgerReq {

    Ledger ledger;
    String userid;
    String ledgerid;

}
