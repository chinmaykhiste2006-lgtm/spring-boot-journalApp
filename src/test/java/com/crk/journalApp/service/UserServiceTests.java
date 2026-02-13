package com.crk.journalApp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import com.crk.journalApp.scheduler.UserScheduler;

@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void mail() {
        userScheduler.fetchUsersAndSendSaMail();
    }
}
