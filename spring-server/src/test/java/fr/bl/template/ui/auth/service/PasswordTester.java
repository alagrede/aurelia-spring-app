package fr.bl.template.ui.auth.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordTester {

	@Test
	public void testPassword() {
		String password = "123456";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
	}

	@Test
	public void testPasswordMatches() {
		String password = "passfa143c";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean result = passwordEncoder.matches("passfa143c", "$2a$10$GkERd5q77mpMTloozifD0Or1gb6P7Cijz5m3NlM4UYzD.c6T9sDUK");
		Assert.assertEquals(true, result);
	}

	@Test
	public void testPassword2() {
		String password = "123456";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
	}
	
}
