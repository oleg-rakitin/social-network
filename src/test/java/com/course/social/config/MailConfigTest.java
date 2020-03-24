package com.course.social.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MailConfig.class)
public class MailConfigTest {

    @Autowired
    private MailConfig mailConfig;

    @Test
    public void getMailSender() {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) mailConfig.getMailSender();
        assertNotNull(mailSender);
    }
}
