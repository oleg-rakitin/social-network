package com.course.social.hooks;

import com.codeborne.selenide.WebDriverRunner;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginHook {

    @Before
    public synchronized void init() {
        System.out.println("TEST");
        // Configuration.headless = true;
        //setRemoteDriver();
    }

    @After
    public void clearCache() {
        WebDriverRunner.getSelenideDriver().getWebDriver().quit();
        WebDriver currentDriver = Serenity.getWebdriverManager().getCurrentDriver();
        if (currentDriver != null) {
            currentDriver.quit();
        }
    }
}
