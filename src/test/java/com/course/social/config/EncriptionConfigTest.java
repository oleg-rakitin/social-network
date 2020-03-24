package com.course.social.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EncriptionConfig.class)
public class EncriptionConfigTest {

    @Autowired
    private EncriptionConfig securityConfiguration;

    @Test
    public void getPasswordEncoder() {
        final BCryptPasswordEncoder encoder = (BCryptPasswordEncoder) securityConfiguration.getPasswordEncoder();
        final String encodedPassword = encoder.encode("password");
        assertNotNull(encodedPassword);
    }
}
