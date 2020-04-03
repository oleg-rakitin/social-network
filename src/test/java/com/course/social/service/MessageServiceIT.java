package com.course.social.service;

import com.course.social.domain.User;
import com.course.social.repos.MessageRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageServiceIT {

    @Mock
    private MessageRepo messageRepo;

    @InjectMocks
    private MessageService messageService;

    @Test
    public void messageList() {
        User user = new User();
        String filter = "test";
        Pageable pageable = PageRequest.of(0, 2);
        when(messageRepo.findByTag(filter, pageable, user)).thenReturn(any(), any(), any());
        assertNull(messageService.messageList(pageable, "tete", user));
        assertNull(messageService.messageList(pageable, null, user));
    }


    @Test
    public void messageListForUser() {
        User user = new User();
        User user1 = new User();
        Pageable pageable = PageRequest.of(0, 2);
        when(messageRepo.findByUser(pageable, user, user1)).thenReturn(any(), any(), any());
        messageService.messageListForUser(pageable, user, user1);
    }

}
