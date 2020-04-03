package com.course.social.util;


import org.apache.commons.lang3.RandomStringUtils;

public class GenerateRandomString {

    public static String generateLongString() {
        return generateString(130);
    }

    public static String generateWithSpecialSymbol() {
        int leftLimit = 33;
        int rightLimit = 123;
        StringBuilder buffer = new StringBuilder(rightLimit - leftLimit);
        for (int i = leftLimit; i < rightLimit; i++) {
            buffer.append((char) i);
        }
        return buffer.append(generateString(130 - buffer.length())).toString();
    }

    public static String generateString(int num) {
        return RandomStringUtils.random(num, true, true);
    }
}
