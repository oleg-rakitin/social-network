package com.course.social.domain;

import org.junit.Assert;
import org.junit.Test;

public class RoleTest {

    @Test
    public void roleTest(){
        Assert.assertEquals("USER",Role.USER.getAuthority());
        Assert.assertEquals("ADMIN",Role.ADMIN.getAuthority());
    }

}
