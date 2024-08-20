package com.example.transactionsystem.controller;


import com.example.transactionsystem.model.Profile;
import com.example.transactionsystem.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile)
    {

        return  ResponseEntity.ok(profileService.updateProfile(
                profile.getUserId(),
                profile.getFullName(),
                profile.getEmail(),
                profile.getPhoneNumber()
        ));

    }


}
