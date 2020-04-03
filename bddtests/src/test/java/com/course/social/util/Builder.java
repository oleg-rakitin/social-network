package com.course.social.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Builder {
    public static final String loginPropertiesUrl = "pages.properties";

    public static PageUrlsProperties getPageUrlsProperties() {
        PageUrlsProperties pageUrlsProperties = null;

        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(loginPropertiesUrl)) {

            Properties properties = new Properties();
            properties.load(input);

            pageUrlsProperties = new PageUrlsProperties();

            pageUrlsProperties.setLoginUrl(properties.getProperty("page.base.url"));

            return pageUrlsProperties;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pageUrlsProperties;
    }

    public static Map<String, String> getRemoteWebDriverProperties() {
        Map<String, String> remoteWebDriver = new HashMap<>();

        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(loginPropertiesUrl)) {

            Properties properties = new Properties();
            properties.load(input);

            remoteWebDriver.put("driverStatus", properties.getProperty("remote.web.driver.enable"));
            remoteWebDriver.put("driverUrl", properties.getProperty("remote.web.driver.url"));

            return remoteWebDriver;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return remoteWebDriver;
    }
}
