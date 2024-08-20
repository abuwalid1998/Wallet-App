package com.example.transactionsystem.service;


import com.example.transactionsystem.model.AddLedgerReq;
import com.example.transactionsystem.model.Ledger;
import com.example.transactionsystem.model.Wallet;
import com.example.transactionsystem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServices {


    @Autowired
    WalletRepository walletRepository;


    public AddLedgerReq addledger(Ledger ledger, String userid, String id) {

        Wallet wallet = walletRepository.findByUserId(userid);
        var legers = wallet.getLedgers();
        legers.put(id,ledger);


        walletRepository.save(wallet);

        return new AddLedgerReq(ledger,userid,id);
    }



}
