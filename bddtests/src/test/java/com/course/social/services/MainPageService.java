package com.course.social.services;

import com.codeborne.selenide.WebDriverRunner;
import com.course.social.util.Builder;
import com.course.social.util.GenerateRandomString;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.xpath;

public class MainPageService {

    public void openMainPage() {
        open(Builder.getPageUrlsProperties().getLoginUrl());
    }

    public boolean checkThatPageOpened(String page) {
        UtilService.waitHalfOfSecond();
        switch (page) {
            case "login": {
                return $(xpath("//div[contains(text(),'Please, login')]")).isDisplayed();
            }
            case "registration": {
                return $(xpath("//div[contains(text(),'Register')]")).isDisplayed();
            }
            case "user list": {
                return $(xpath("//div[contains(text(),'List of users')]")).isDisplayed();
            }
        }
        return false;
    }

    public void clickToButton(String button) {
        switch (button) {
            case "registration": {
                $(xpath("//a[contains(text(),'Register')]")).shouldBe(appear).click();
                break;
            }
            case "create": {
                $(xpath("//button[contains(text(),'Create')]")).shouldBe(appear).click();
                break;
            }
            case "Add a post": {
                $(xpath("//div//a[contains(text(),'Add a post')]")).shouldBe(appear).click();
                break;
            }
            case "publish": {
                $(xpath("//button[contains(text(),'Опубликовать запись')]")).shouldBe(appear).click();
                break;
            }
            case "subscribe": {
                $(xpath("//a[contains(text(),'Subscribe')]")).shouldBe(appear).click();
                break;
            }
            case "unsubscribe": {
                $(xpath("//a[contains(text(),'Unsubscribe')]")).shouldBe(appear).click();
                break;
            }
            case "save": {
                $(xpath("//button[contains(text(),'Save')]")).shouldBe(appear).click();
            }
        }
    }

    public void refreshPage(String arg0) {
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        webDriver.navigate().refresh();
        // webDriver.get(webDriver.getCurrentUrl());
    }

    public void sendToInput(String input, String data) {
        switch (input) {
            case "username": {
                $(xpath("//input[contains(@name,'username')]")).sendKeys(data);
                break;
            }
            case "password": {
                $(xpath("//input[contains(@name,'password')]")).sendKeys(data);
                break;
            }
            case "password2": {
                $(xpath("//input[contains(@name,'password2')]")).sendKeys(data);
                break;
            }
            case "email": {
                $(xpath("//input[contains(@name,'email')]")).clear();
                $(xpath("//input[contains(@name,'email')]")).sendKeys(data);
                break;
            }
            case "about": {
                if (data.equals("random")) {
                    String str = GenerateRandomString.generateString(20);
                    Serenity.setSessionVariable("about_random_string").to(str);
                    $(xpath("//input[contains(@name,'text')]")).sendKeys(str);
                } else {
                    $(xpath("//input[contains(@name,'text')]")).sendKeys(data);
                }
                break;
            }
            case "tag": {
                $(xpath("//input[contains(@name,'tag')]")).sendKeys(data);
                break;
            }
            case "old password": {
                $(xpath("//input[contains(@name,'oldPassword')]")).sendKeys(data);
                break;
            }
            case "new password": {
                $(xpath("//input[contains(@name,'newPassword')]")).sendKeys(data);
                break;
            }
        }
    }

    public void checkRecaptcha() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        ;
        js.executeScript("document.getElementById('recaptcha-anchor').click()");
        // executeJavaScript("document.getElementById('recaptcha-anchor').click()");
        //  $(xpath("//div[contains(@class,'rc-anchor rc-anchor-normal rc-anchor-light')]")).shouldBe(appear).click();
    }

    public void goToPage(String page) {
        $(xpath("//ul//li//a[contains(text(),'" + page + "')]")).shouldBe(appear).click();
    }

    public boolean checkThatMessageAdded() {
        UtilService.waitHalfOfSecond();
        return $(xpath("//div[contains(@class,'m-2')]//span[contains(text(),'" +
                Serenity.sessionVariableCalled("about_random_string").toString() +
                "')]")).isDisplayed();
    }

    public void logout() {
        $(xpath("//button[contains(text(),'Sign Out')]")).shouldBe(appear).click();
    }
}
