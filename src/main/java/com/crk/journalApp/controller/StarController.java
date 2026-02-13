package com.crk.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.crk.journalApp.model.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.crk.journalApp.service.UserService;

@RestController
@RequestMapping("/star")
public class StarController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<String>> getStarredEntries() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);

        List<String> all = user.getStarredEntries();

        return new ResponseEntity<>(all, HttpStatus.OK);

    }

    @PostMapping("/add/id/{id}")
    public void addStarredEntries(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        user.getStarredEntries().add(id);

        userService.saveExistingUser(user);

    }

    @DeleteMapping("/delete/id/{id}")
    public void deleteStarredEntries(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        user.getStarredEntries().removeIf(x -> x.equals(id));

        userService.saveExistingUser(user);
    }

}
