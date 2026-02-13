package com.crk.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crk.journalApp.service.UserDetailsServiceImpl;
import com.crk.journalApp.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


import com.crk.journalApp.model.User;
import com.crk.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;



import org.springframework.web.bind.annotation.RequestParam;

import com.crk.journalApp.service.WeatherService;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("health-check")
    public String HealthCheck() {
        return "ok";
    }

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings(String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            String greeting = "Welcome " + userName + " Weather feels like "
                    + weatherService.getWeather("Pandharpur").getCurrent().getTemperature();
            return new ResponseEntity<>(greeting, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
           UserDetails userDetails =  userDetailsService.loadUserByUsername(user.getUsername());
   String jwt = jwtUtil.generateToken(userDetails.getUsername());
   return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
log.error("some error occured", e);
return new ResponseEntity<>("incorrect username or password", HttpStatus.BAD_REQUEST);
        }

        
    }


}
