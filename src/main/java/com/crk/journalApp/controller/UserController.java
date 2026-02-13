package com.crk.journalApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.crk.journalApp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crk.journalApp.model.JournalEntry;
import com.crk.journalApp.service.JournalEntryService;
import com.crk.journalApp.service.UserService;
import com.crk.journalApp.service.EmailService;

import org.springframework.web.bind.annotation.RequestParam;

import com.crk.journalApp.api.response.WeatherResponse;
import com.crk.journalApp.repository.UserRepositoryImpl;
import com.crk.journalApp.service.EmailService;
import com.crk.journalApp.service.WeatherService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private EmailService emailService;

    @PutMapping
    public User updateUser(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);

        if (userInDb != null) {
            userInDb.setUsername(user.getUsername() != null ? user.getUsername() : userInDb.getUsername());
        
            userInDb.setEmail(user.getEmail() != null ? user.getEmail() : userInDb.getEmail());
            if (user.getSentimentAnalysis() != null) {
                userInDb.setSentimentAnalysis(user.getSentimentAnalysis());
            }

            if(user.getPassword()!=null&&!user.getPassword().isBlank()){
                userService.saveExistingUser(userInDb, user);
            }
            else{
                userService.saveExistingUser(userInDb);
            }
            
        }

        return userInDb;
    }

    @DeleteMapping
    @Transactional
    public void deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            int entriesSize = 0;
            if (user.getJournalEntries() != null) {
                entriesSize = user.getJournalEntries().size();
                for (int i = 0; i < entriesSize; i++) {
                    journalEntryService.deleteById(user.getJournalEntries().get(i).getId());
                }
            }
            userService.deleteById(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("some error occurred while deleting", e);
        }

    }

    @GetMapping("/chinmay")
    public void getUserByName() {
        emailService.sendEmail("hariom.indrale24@vit.edu", "spring boot learning",
                "this mail is sent to you using spring boot");
    }

    @GetMapping("/greetings/{city}")
    public ResponseEntity<?> greetUser(@PathVariable String city) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            String greeting = "";
            if (weatherResponse != null) {
                greeting = " weather feels like " + weatherResponse.getCurrent().getFeelsLike();
            }
            return new ResponseEntity<>("Hi " + username + greeting, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("some error occurred while greeting", e);
        }

    }

};
