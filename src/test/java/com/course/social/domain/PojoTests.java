package com.course.social.domain;

import com.course.social.domain.dto.CaptchaResponseDto;
import com.course.social.domain.dto.MessageDto;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;

import static pl.pojo.tester.api.assertion.Method.*;

public class PojoTests {

    @Test
    public void allWithoutConstructor() {
        Assertions.assertPojoMethodsForAll(
                FeedBack.class,
                Message.class,
                CaptchaResponseDto.class)
                .testing(GETTER, SETTER, TO_STRING)
                .areWellImplemented();
    }

    @Test
    public void withoutToString() {
        Assertions.assertPojoMethodsForAll(
                User.class)
                .testing(GETTER, SETTER)
                .areWellImplemented();

    }

    @Test
    public void constructorTest() {
        Assertions.assertPojoMethodsForAll(
                Message.class)
                .testing(CONSTRUCTOR)
                .areWellImplemented();
    }

    @Test
    public void gettersToString() {
        Assertions.assertPojoMethodsForAll(
                MessageDto.class)
                .testing(GETTER, TO_STRING)
                .areWellImplemented();
    }
}
