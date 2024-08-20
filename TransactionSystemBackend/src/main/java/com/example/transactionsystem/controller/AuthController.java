package com.example.transactionsystem.controller;

import com.example.transactionsystem.model.AuthResponse;
import com.example.transactionsystem.model.RegesterResponse;
import com.example.transactionsystem.model.RegisterResopnse;
import com.example.transactionsystem.model.User;
import com.example.transactionsystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResopnse> register(@RequestBody User user) {
        try {
            RegesterResponse result = authenticationService.register(user);

            boolean flag = result.getProfile().getId().isEmpty();

            RegisterResopnse registerResopnse = new RegisterResopnse();


            if (flag){
                registerResopnse.setFlag(false);
                registerResopnse.setUserId("");
                return ResponseEntity.ok(registerResopnse);
            }

            registerResopnse.setUserId(user.getId());
            registerResopnse.setFlag(true);
            return ResponseEntity.ok(registerResopnse);

        }catch (Exception e){
            RegisterResopnse registerResopnse = new RegisterResopnse();
            registerResopnse.setFlag(false);
            registerResopnse.setUserId("");
            return ResponseEntity.ok(registerResopnse);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestHeader String username, @RequestHeader String password) {
        try {
            System.out.println(username);
            System.out.println(password);
            String token = authenticationService.login(username, password);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(e.getMessage()));
        }
    }
}
