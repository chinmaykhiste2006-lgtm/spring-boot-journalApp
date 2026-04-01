package com.crk.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.crk.journalApp.service.UserService;
import com.crk.journalApp.service.UserDetailsServiceImpl;
import com.crk.journalApp.model.User;

@RestController
@RequestMapping("/auth/google")
@Slf4j
public class GoogleAuthController{


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @value("${spring.security.oauth2.client.regestration.google.client-id}")
    private String clientId;

    @value("${spring.security.oauth2.client.regestration.google.client-secret}")
    private String clientSecret;

@GetMapping("/callback")
public ResponseEntity<?> handleGoogleCallback(@RequestParam String code){

    try{

String tokenEndpoint = "https://oauth2.googleapis.com/token";

Map<String, String> params = new HashMap<>();
params.put("code", code);
params.put("client_id", clientId);
params.put("client_secret", clientSecret);
params.put("redirect_uri", "https://developers.google.com/oauthplayground");
params.put("grant_type", "authorization_code");

HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

HttpEntity<Map<String, String>> equest = new HttpEntity<>(params, headers);

ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
String idToken = (String) tokenResponse.getBody().get("id_token");
String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
ResponseEntity<Map> userInfoResponse = restTemplate.postForEntity(userInfoUrl, Map.class);

if(userInfoResponse.getStatusCode() == HttpStatus.OK){

    Map<String, Object> userInfo = userInfoResponse.getBody();
    String email = (String) userInfo.get("email");
     String username = (String) userInfo.get("name");
     User user = userService.findByEmail(email);

     if(user == null){

        User user = new User;
        user.setEmail(email);
        user.setUsername(username);
        userService.saveUser(user);
        userDetails = userDetailsService.loadUserByUsername(userame);

     }
     UsernamePasswordAuthenticationToken authentication = 
     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
     SecurityContextHolder.getContext.setAuthentication(authentication);

     return ResponseEntity.status(HttpStatus.OK).build();


}
 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }catch(Exception e){
log.error("Exception", e);
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        
    }

    
}
  
    
}