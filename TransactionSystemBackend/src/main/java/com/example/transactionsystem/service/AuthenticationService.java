package com.example.transactionsystem.service;


import com.example.transactionsystem.model.*;
import com.example.transactionsystem.repository.ProfileRepository;
import com.example.transactionsystem.repository.UserRepository;
import com.example.transactionsystem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.UUID;


@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ProfileRepository profileRepository;

    public RegesterResponse register(User user) {

        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        System.out.println(user1);

        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {


            System.out.println("Start Reg");

            Ledger ledger = new Ledger();
            ledger.setId(user.getId());
            ledger.setBalance(0);
            ledger.setId("1");
            ledger.setCurrency(String.valueOf(Currancies.USD));

            Map map = new HashMap();
            map.put(ledger.getId(), ledger);

            user.setPassword(passwordEncoder.encode(user.getPassword()));


            RegesterResponse response = new RegesterResponse();
            Profile profile = new Profile();
            Wallet wallet = new Wallet();
            String msg = "User registered successfully";

            profile.setUserId(user.getId());
            profile.setEmail(user.getEmail());
            profile.setFullName(user.getUsername());

            UUID uuid = UUID.randomUUID();

            wallet.setId(uuid.toString());
            wallet.setUserId(user.getId());
            wallet.setLedgers(map);


            response.setProfile(profile);
            response.setWallet(wallet);
            response.setMessage(msg);



            System.out.println("Saving Data into data base");
            userRepository.save(user);
            walletRepository.save(wallet);
            profileRepository.save(profile);

            return response;
        }else{
            System.out.println("Registration failed");
            String msg = "User found with username " + user.getUsername();
            return new RegesterResponse(null,null,msg);
        }
    }

    public String login(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));

        System.out.println(user.getPassword());
        System.out.println(user.getId());

        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(username);
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}