package com.course.social.service;


import com.course.social.domain.Role;
import com.course.social.domain.User;
import com.course.social.repos.UserRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIT {

    @Autowired
    UserSevice userSevice;

    @Autowired
    UserRepo userRepo;

    private User user;

    @Before
    public void before() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user = new User();
        user.setRoles(roles);
        user.setUsername("test_user");
        user.setEmail("test@test");
        user.setPassword("twqtqwt");
        user.setActivationCode("activation_code");
        user.setActive(false);
    }

    @Test
    public void addUser() {
        userSevice.addUser(user);
        assertNotNull(userRepo.findByUsername("test_user"));
    }

    @Test
    public void loadUserByUsername() {
        userRepo.save(user);
        User loadedUser = (User) userSevice.loadUserByUsername("test_user");
        assertNotNull(loadedUser);
        assertEquals("test_user", loadedUser.getUsername());
    }

    @Test
    public void activate() {
        userRepo.save(user);
        assertTrue(userSevice.activateUser("activation_code"));
    }

    @Test
    public void saveUser() {
        user.setUsername("test_save");
        userRepo.save(user);
        userSevice.saveUser(user, "test_user", new HashMap<>());
        assertNotNull(userRepo.findByUsername("test_user"));
    }

    @Test
    public void subscribe() {
        User user1 = new User();
        user1.setUsername("test_user1");
        user1.setEmail("email@test.com");
        user1.setPassword("tete");
        userRepo.save(user1);
        userSevice.subscribe(user1, user);
        User user2 = userRepo.findByUsername("test_user");
        assertEquals(1, user2
                .getSubscribers().size());
        userSevice.unsubscribe(user1, user);
        userRepo.delete(user1);
    }

    @Test
    public void unsubscribe() {
        User user1 = new User();
        user1.setUsername("test_user1");
        user1.setEmail("email@test.com");
        user1.setPassword("tete");
        userRepo.save(user1);
        userSevice.subscribe(user1, user);
        User user2 = userRepo.findByUsername("test_user");
        assertEquals(1, user2
                .getSubscribers().size());
        userSevice.unsubscribe(user1, user);
        user2 = userRepo.findByUsername("test_user");
        assertEquals(0, user2
                .getSubscribers().size());
        userRepo.delete(user1);
    }

    @After
    public void after() {
        userRepo.delete(userRepo.findByUsername("test_user"));
    }

}
