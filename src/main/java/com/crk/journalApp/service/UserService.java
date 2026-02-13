package com.crk.journalApp.service;

import java.util.Arrays;

import com.crk.journalApp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crk.journalApp.model.JournalEntry;
import com.crk.journalApp.repository.JournalEntryRepository;
import com.crk.journalApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setSentimentAnalysis(user.getSentimentAnalysis());
            user.setRoles(List.of("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.info("an error occured", e);
        }

    }

    public void saveExistingUser(User userInDb, User user) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userInDb.setPassword(user.getPassword());
        userRepository.save(userInDb);
    }

    public void saveExistingUser(User user){

        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}