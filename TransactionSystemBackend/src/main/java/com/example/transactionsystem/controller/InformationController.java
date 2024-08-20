package com.example.transactionsystem.controller;


import com.example.transactionsystem.model.Ledger;
import com.example.transactionsystem.model.Transaction;
import com.example.transactionsystem.model.Wallet;
import com.example.transactionsystem.repository.UserRepository;
import com.example.transactionsystem.service.AccountServices;
import com.example.transactionsystem.service.TransactionService;
import com.example.transactionsystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/info")
public class InformationController {

    @Autowired
    AccountServices accountServices;

    @Autowired
    TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/get-all")
    public List<Transaction> getAll(@RequestHeader String Userid) {

        return transactionService.getalltransactions(Userid);

    }

    @PostMapping("/get-user-wallet")
    public @ResponseBody Wallet getUserWallet(@RequestHeader String Userid) {

        String code = userRepository.findUserByUsername(Userid).get().getId();


        System.out.println("Searching for wallet id for "+ code);

        return walletService.getWalletByUserId(code);
    }

    @PostMapping("/get-user-ledgers")
    public @ResponseBody Object[] getUserLedgers(@RequestHeader String walletid) {





        List<Ledger> ledgers = new ArrayList<>();

        Wallet wallet = walletService.getWalletByKey(walletid);

        var map = wallet.getLedgers().values().toArray();

        for (Object ledger : map) {
            System.out.println(ledger.toString());
        }

        return map;
    }

}
