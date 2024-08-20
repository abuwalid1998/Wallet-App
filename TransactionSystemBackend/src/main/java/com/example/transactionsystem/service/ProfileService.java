package com.example.transactionsystem.service;

import com.example.transactionsystem.model.Profile;
import com.example.transactionsystem.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(String userId, String fullName, String email, String phoneNumber) {
        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setFullName(fullName);
        profile.setEmail(email);
        profile.setPhoneNumber(phoneNumber);
        return profileRepository.save(profile);
    }

    public Profile getProfileByUserId(String userId) {
        return profileRepository.findByUserId(userId);
    }

    public Profile updateProfile(String userId, String fullName, String email, String phoneNumber) {
        Profile profile = profileRepository.findByUserId(userId);
        if (profile != null) {
            profile.setFullName(fullName);
            profile.setEmail(email);
            profile.setPhoneNumber(phoneNumber);
            return profileRepository.save(profile);
        }
        return null; // or throw an exception if the profile is not found
    }

}
