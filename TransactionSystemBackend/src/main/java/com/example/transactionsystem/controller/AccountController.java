package com.example.transactionsystem.controller;


import com.example.transactionsystem.model.*;
import com.example.transactionsystem.service.AccountServices;
import com.example.transactionsystem.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountServices accountServices;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/add-ledger")
    public AddLedgerReq addLedger(@RequestBody AddLedgerReq ledger) {
        return accountServices.addledger(
                ledger.getLedger(),
                ledger.getUserid(),
                ledger.getLedgerid()
        );
    }


    @PostMapping("buy-coins")
    public Transaction buyCoins(@RequestBody BuyRequest buyRequest) {
      return   transactionService.buy(
              buyRequest.getWalletid(),
                buyRequest.getAmount(),
                buyRequest.getLedcode()
        );
    }

    @PostMapping("sell-coins")
    public Transaction buyCoins(@RequestBody SellRequest sellRequest) {
        return   transactionService.sell(
                sellRequest.getWalletid(),
                sellRequest.getAmount(),
                sellRequest.getLedcode()
        );
    }


    @PostMapping("Transfer-coins")
    public Transaction Transfer(@RequestBody TransferRequest request) {
        System.out.println(request.getFromWalletId());
        System.out.println(request.getFromWalletId());
        return   transactionService.transfer(
                request.getFromWalletId(),
                request.getToWalletId(),
                request.getAmount(),
                request.getCurcode()
        );
    }

    @PostMapping("add-usd")
    public Transaction Transfer(@RequestHeader String walletid , @RequestHeader double amount) {
        System.out.println(walletid);
        return   transactionService.addUsd(
                walletid,
                amount
        );

    }



}
