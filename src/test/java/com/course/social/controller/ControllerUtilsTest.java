package com.course.social.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerUtilsTest {

    @Test
    public void getErrors() {
        Object target;
        BindingResult result = new BeanPropertyBindingResult("te","te" );// mock(BindingResult.class);
        assertNotNull(ControllerUtils.getErrors(result));

    }
}
