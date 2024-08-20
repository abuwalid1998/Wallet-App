package com.example.transactionsystem.service;

import com.example.transactionsystem.model.*;
import com.example.transactionsystem.repository.ProfileRepository;
import com.example.transactionsystem.repository.TransactionRepository;
import com.example.transactionsystem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public Transaction buy(String walletId, double amount, String ledgerCode) {

        Wallet wallet = walletRepository.findById(walletId).get();
        System.out.println(wallet.getId());
        if (wallet == null) return null;

        ledgerCode = ledcodeMapping(ledgerCode);

        Ledger ledgerTo = wallet.getLedgers().get(ledgerCode);
        Ledger ledgerFrom = wallet.getLedgers().get("1");
        double balance = ledgerFrom.getBalance();
        if (ledgerTo == null || ledgerFrom == null) {
            System.out.println("Im Here >>>>>>>>");
            Ledger ledger = new Ledger(
                    ledgerCode,
                    ledcodeName(ledgerCode),
                    0.0
            );
            ledgerTo = ledger;
            var map = wallet.getLedgers();
            map.put(ledgerCode, ledger);
            wallet.setLedgers(map);
            System.out.println("Wallet Updated >>>");
            walletRepository.save(wallet);
        };

        double fxRate;
        String description;

        switch (ledgerCode) {
            case "100":
                System.out.println("bitcoin");
                fxRate = 0.000017;
                description = "Buy Bitcoin";
                break;
            case "200":
                fxRate = 0.00038;
                description = "Buy Ethereum";
                break;
            case "300":
                fxRate = 120.1718;
                description = "Buy FOX";
                break;
            default:
                return null;
        }

        double totalPrice = amount / fxRate;
        //Balnce Validation
        if (balance < totalPrice ){
            return null;
        }

        ledgerTo.setBalance(ledgerTo.getBalance() + amount);
        ledgerFrom.setBalance(ledgerFrom.getBalance() - totalPrice);
        walletRepository.save(wallet);
        System.out.println("Wallet Saved");
        Transaction transaction = createTransaction(walletId, wallet, wallet, totalPrice, fxRate, description);
        transactionRepository.save(transaction);
        System.out.println("Transaction Saved");

        return transaction;
    }
    public Transaction sell(String walletId, double amount, String ledgerCode) {
        Wallet wallet = walletRepository.findById(walletId).get();
        System.out.println("Im here 1");
        System.out.println(wallet.getId());
        if (wallet == null) return null;

        ledgerCode = ledcodeMapping(ledgerCode);

        Ledger ledgerFrom = wallet.getLedgers().get(ledgerCode);
        Ledger ledgerTo = wallet.getLedgers().get("1"); // Assuming "1" is the USD ledger
        double balance = ledgerFrom.getBalance();
        if (ledgerFrom == null || ledgerTo == null) return null;

        System.out.println("Im here 2");
        double fxRate;
        String description;




        switch (ledgerCode) {
            case "100":
                fxRate = 58361.48;
                description = "Sell Bitcoin";
                break;
            case "200":
                fxRate = 2614.34;
                description = "Sell Ethereum";
                break;
            case "300":
                fxRate = 120.1718;
                description = "Sell FOX";
                break;
            default:
                return null;
        }

        double totalPrice = amount * fxRate;
        //Balnce Validation
        if (balance < amount){
            return null;
        }
        System.out.println("Im here 3");

        ledgerTo.setBalance(ledgerTo.getBalance() + totalPrice);
        ledgerFrom.setBalance(ledgerFrom.getBalance() - amount);

        walletRepository.save(wallet);

        Transaction transaction = createTransaction(walletId, wallet, wallet, totalPrice, fxRate, description);
        transactionRepository.save(transaction);
        System.out.println("Sold");
        return transaction;
    }

    public Transaction transfer(String fromWalletId, String toWalletId, double amount, String currencyCode) {
        Wallet fromWallet = walletRepository.findById(fromWalletId).get();
        Wallet toWallet = walletRepository.findById(toWalletId).get();

        System.out.println("Im here 1");

        String ledgerCode = ledcodeMapping(currencyCode);

        System.out.println(">>>>>>>"+ledgerCode);

        Ledger fromLedger = fromWallet.getLedgers().get(ledgerCode);
        Ledger toLedger = toWallet.getLedgers().get(ledgerCode);


        if (fromLedger == null || toLedger == null) return new Transaction();

        System.out.println("Im here 2");
        fromLedger.setBalance(fromLedger.getBalance() - amount);
        toLedger.setBalance(toLedger.getBalance() + amount);

        var fromledgers = fromWallet.getLedgers();
        var toledgers = toWallet.getLedgers();

        fromledgers.put(ledgerCode,fromLedger);
        toledgers.put(ledgerCode,toLedger);

        fromWallet.setLedgers(fromledgers);
        toWallet.setLedgers(toledgers);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        String description = "Transfer from " + fromWalletId + " to " + toWalletId;

        Transaction transaction = createTransaction(fromWalletId, fromWallet, toWallet, amount, 1, description);

        transactionRepository.save(transaction);

        System.out.println("Im here 3");
        return transaction;
    }

    public Transaction addUsd(String walletId, double amount) {
        System.out.println(walletId);
        Wallet wallet = walletRepository.findById(walletId).get();

        Ledger usdLedger = wallet.getLedgers().get("1"); // Assuming "1" is the USD ledger


        usdLedger.setBalance(usdLedger.getBalance() + amount);
        var map = wallet.getLedgers();
        map.put("1",usdLedger);
        wallet.setLedgers(map);

        walletRepository.save(wallet);



        Transaction transaction = createTransaction(walletId, wallet, wallet, amount, 1, "Add USD");

        transactionRepository.save(transaction);

        return transaction;
    }

    private Transaction createTransaction(String walletId, Wallet ledgerFrom, Wallet ledgerTo, double amount, double fxRate, String description) {
        return new Transaction(
                UUID.randomUUID().toString(),
                LocalDateTime.now().toString(),
                description,
                ledgerFrom.getId(),
                ledgerTo.getId(),
                String.valueOf(amount),
                String.valueOf(1),
                String.valueOf(fxRate)
        );
    }

    public List<Transaction> getalltransactions(String walletid){
       List<Transaction> tra = transactionRepository.findAllByFromidOrToid(walletid,walletid);
       System.out.println("Transaction Size is : "+tra.size());
        return transactionRepository.findAllByFromidOrToid(walletid,walletid);
    }

    private String ledcodeMapping(String newledcode){
        if(newledcode.equals("BITCOIN")){
            return "100";
        }else if(newledcode.equals("ETH")){
            return "200";
        }else if (newledcode.equals("FOX")){
            return "300";
        }else{
            return "1";
        }
    }

    private String ledcodeName(String newledcode){
        if(newledcode.equals("100")){
            return Currancies.BITCOIN.toString();
        }else if(newledcode.equals("200")){
            return Currancies.ETHEREUM.toString();
        }else if(newledcode.equals("300")){
            return Currancies.FOX.toString();
        }else {
            return Currancies.USD.toString();
        }
    }

}
