package com.example.transactionsystem.service;

import com.example.transactionsystem.model.Ledger;
import com.example.transactionsystem.model.Wallet;
import com.example.transactionsystem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet createWallet(String userId) {
        Wallet wallet = new Wallet();
        Ledger ledger = new Ledger();
        ledger.setCurrency("1");
        ledger.setId("1");
        ledger.setBalance(0.00);
        wallet.setUserId(userId);
        wallet.setId(userId);
        var ledgers = wallet.getLedgers();

        if (ledgers.isEmpty())
        {
            ledgers.put("1", ledger);
        }

        wallet.setLedgers(ledgers);

        // Initialize ledgers if needed
        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }

    public Wallet getWalletByKey(String id){
        return walletRepository.findById(id).get();
    }

}
