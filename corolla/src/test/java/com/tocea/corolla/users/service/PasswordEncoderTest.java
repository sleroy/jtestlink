package com.tocea.corolla.users.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tocea.corolla.CorollaProgramApplication;
import com.tocea.corolla.users.api.IPasswordEncoder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CorollaProgramApplication.class)
@WebAppConfiguration
public class PasswordEncoderTest {

	@Autowired
	private IPasswordEncoder	passwordEncoder;

	@Test
	public final void testEncodePassword() {
		final String encodePassword = this.passwordEncoder.encodePassword("rawpassword");
		assertTrue(this.passwordEncoder.isPasswordMatching("rawpassword", encodePassword));
		assertFalse(this.passwordEncoder.isPasswordMatching("rawPassword", encodePassword));
	}

}