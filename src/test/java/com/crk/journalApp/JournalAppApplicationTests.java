package com.crk.journalApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crk.journalApp.service.UserService;

@SpringBootTest
class JournalAppApplicationTests {

	@Autowired
	private UserService userService;

	@Disabled
	@ParameterizedTest
	@CsvSource({
			"Chinmay",
			"Aaji"
	})
	void testFindByUserName(String name) {
		assertNotNull(userService.findByUsername(name));
	}

	@Disabled
	@Test
	void contextLoads() {
	}

	@Disabled
	@ParameterizedTest
	@CsvSource({
			"1,1,2",
			"2,10,12",
			"3,3,9"
	})
	void test(int a, int b, int expected) {
		assertEquals(expected, a + b);
	}
}
