package com.course.social.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MailSenderTest {

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
