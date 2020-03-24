package com.course.social.service;

import com.course.social.config.EncriptionConfig;
import com.course.social.domain.Role;
import com.course.social.domain.User;
import com.course.social.repos.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EncriptionConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserSeviceTest {

    @Autowired
    EncriptionConfig getPasswordEncoder;
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserSevice userSevice;

    @Test
    public void loadUserByUsername() {
        User user = new User();
        user.setUsername("test_username");
        when(userRepo.findByUsername(Mockito.anyString())).thenReturn(user);
        User userLoaded = (User) userSevice.loadUserByUsername("test_username");
        assertEquals("test_username", userLoaded.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameException() {
        when(userRepo.findByUsername(Mockito.anyString())).thenReturn(null);
        User userLoaded = (User) userSevice.loadUserByUsername("test_username");
        assertEquals("test_username", userLoaded.getUsername());
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setPassword("test");
        when(userRepo.findByUsername(Mockito.anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());
        assertTrue(userSevice.addUser(user));
        verify(userRepo).save(any());
        user.setPassword(null);
        assertFalse(userSevice.addUser(user));
    }

    @Test(expected = NullPointerException.class)
    public void addUserNpe() {
        User user = new User();
        user.setPassword("test");
        user.setEmail("test");
        assertTrue(userSevice.addUser(user));
    }

    @Test
    public void addUserNullUser() {
        User user = new User();
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        assertFalse(userSevice.addUser(new User()));
    }

    @Test
    public void activateUser() {
        User user = new User();
        when(userRepo.findByActivationCode("test")).thenReturn(user);
        assertTrue(userSevice.activateUser("test"));
    }

    @Test
    public void activateUserNullUser() {
        when(userRepo.findByActivationCode("test")).thenReturn(null);
        assertFalse(userSevice.activateUser("test"));
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();
        when(userRepo.findAll()).thenReturn(users);
        assertNotNull(userSevice.findAll());
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setRoles(new HashSet<>());
        Map<String, String> set = new HashMap<>();
        set.put(Role.ADMIN.toString(), "");
        set.put("TEST", "");
        userSevice.saveUser(user, "test", set);
        assertEquals("test", user.getUsername());
        set.put(Role.USER.toString(), "");
        userSevice.saveUser(user, "test", set);
        assertEquals("test", user.getUsername());
    }

    @Test
    public void updateProfile() {
        User user = new User();
        user.setEmail("tetet");
        user.setPassword("test");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode("test")).thenReturn(anyString());
        userSevice.updateProfile(user, "test", "test", "email");
        userSevice.updateProfile(user, "test", "test1", "email");
        user.setPassword("test");
        userSevice.updateProfile(user, "test", "", "");
        user.setPassword(null);
        userSevice.updateProfile(user, "tes123t2", "", "");
        user.setEmail(null);
        userSevice.updateProfile(user, "tes123t2", "", "twqt");
        user.setPassword("tes123t2");
        user.setEmail("fwq");
        userSevice.updateProfile(user, "tes123t2", "", null);
    }

    @Test
    public void subscribe() {
        User subscribes = new User();
        subscribes.setUsername("subs");
        User user = new User();
        user.setSubscribers(new HashSet<>());
        userSevice.subscribe(subscribes, user);
        assertEquals(subscribes.getUsername(), user.getSubscribers().iterator().next().getUsername());
    }

    @Test
    public void unsubscribe() {
        User subscribes = new User();
        subscribes.setUsername("subs");
        HashSet<User> set = new HashSet<>();
        set.add(subscribes);
        User user = new User();
        user.setSubscribers(set);
        assertEquals(1, user.getSubscribers().size());
        userSevice.unsubscribe(subscribes, user);
        assertEquals(0, user.getSubscribers().size());
    }
}
