package com.course.social.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailSenderTestIT {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    MailSender mailSender;

    @Test
    public void send() {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());
        mailSender.send("test", "test", "test");
        verify(javaMailSender, atLeastOnce()).send((SimpleMailMessage) any());
    }
}
