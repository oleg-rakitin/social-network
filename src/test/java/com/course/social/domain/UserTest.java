package com.course.social.domain;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UserTest {

    private User user = new User();
    private Set<Role> roles;

    @Before
    public void before() {
        roles = new HashSet<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        user.setRoles(roles);
    }

    @Test
    public void isAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void isActive() {
        user.setActive(false);
        Assert.assertFalse(user.isActive());
    }

    @Test
    public void isEnabled() {
        Assert.assertFalse(user.isEnabled());
    }

    @Test
    public void isAdmin() {
        assertTrue(user.isAdmin());
    }

    @Test
    public void getAuthorities() {
        assertTrue(CollectionUtils.isEqualCollection(roles, user.getRoles()));
    }

    @Test
    public void equals(){
        User user = new User();
        user.setUsername("test");
        user.setId(1L);
        user.setEmail("test@tet");
        User user1= new User();
        user1.setUsername("test");
        user1.setId(1L);
        user1.setEmail("test@tet");
        User user2=user;
        assertTrue(user.equals(user1));
        assertTrue(user.equals(user2));
        assertEquals(user.hashCode(),user1.hashCode());
        assertFalse(user.equals(null));
        assertFalse(user.equals(new Message()));
        user1.setId(2L);
        assertFalse(user.equals(user1));
        user1.setId(1L);
        user1.setUsername("fwfw");
        assertFalse(user.equals(user1));
        user1.setUsername("test");
        user1.setEmail("fwqfqw");
        assertFalse(user.equals(user1));
        user1.setEmail("test@test");
        assertFalse(user.equals(user1));
    }

    @After
    public void after() {
        roles.clear();
    }

}
