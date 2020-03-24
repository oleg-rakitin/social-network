package com.course.social.util;

import com.course.social.domain.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageHelperTest {

    @Test
    public void getAuthorName() {
        User user = new User();
        user.setUsername("test");
        assertEquals("test",MessageHelper.getAuthorName(user));
        assertEquals("<none>",MessageHelper.getAuthorName(null));
    }
}
