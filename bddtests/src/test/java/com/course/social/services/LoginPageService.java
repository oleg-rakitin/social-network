package com.course.social.services;

import com.course.social.util.Builder;
import com.course.social.util.JDBCUtil;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPageService {

    public void login(String userType) {
        open(Builder.getPageUrlsProperties().getLoginUrl());

        switch (userType){
            case "testuserregister":{
                sendValuesToFields("testuserregister", "testuserregister");
                break;
            }
            case "testuserregister123":{
                sendValuesToFields("testuserregister123", "testuserregister");
                break;
            }
        }
    }

    private void sendValuesToFields(String testuser, String testuser1) {
            $(By.xpath("//div//input[contains(@name,'username')]"))
                    .shouldBe(appear)
                    .sendKeys(testuser);
            $(By.xpath("//div//input[contains(@name,'password')]"))
                    .shouldBe(appear)
                    .sendKeys(testuser1);

            $(By.xpath("//button[contains(text(),'Sign In')]"))
                    .shouldBe(appear)
                    .click();
    }

    public boolean checkThatAuthSuccess() {
        return $(By.xpath("//div[contains(text(),'testuserregister')]")).isDisplayed();
    }

    public void clearDatabase() {
        JDBCUtil.clearDataBase();
    }

    public void login(String username, String password) {
        open(Builder.getPageUrlsProperties().getLoginUrl());

        sendValuesToFields(username, password);

    }

    public boolean checkThatAuthNotSuccess() {
        return $(By.xpath("//div[contains(text(),'Bad credentials')]")).isDisplayed();
    }
}
