package com.course.social.domain;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

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

    @After
    public void after() {
        roles.clear();
    }
}
