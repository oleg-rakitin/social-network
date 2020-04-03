package com.course.social.services;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.course.social.util.JDBCUtil;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class MessagesPagesService {

    public void findMessagePostedByUserAndClickLike(String user) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        for (SelenideElement selenideElement : elements) {
            UtilService.waitHalfOfSecond();
            if (selenideElement.getText().contains(user)) {
                selenideElement.find(xpath("//a[contains(@href,'like')]")).click();
            }
        }
    }

    public boolean checkThatPostByUserLiked(String user) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        for (SelenideElement selenideElement : elements) {
            UtilService.waitHalfOfSecond();
            if (selenideElement.getText().contains(user)) {
                if (selenideElement.find(xpath("//div//a//i[contains(@class,'fas fa-heart')]")).isDisplayed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void findMessagePostedByUserAndDislike(String user) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        for (SelenideElement selenideElement : elements) {
            UtilService.waitHalfOfSecond();
            if (selenideElement.getText().contains(user)) {
                selenideElement.find(xpath("//a[contains(@href,'like')]")).click();
            }
        }
    }

    public boolean checkThatPostByUserDisliked(String user) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        for (SelenideElement selenideElement : elements) {
            UtilService.waitHalfOfSecond();
            if (selenideElement.getText().contains(user)) {
                if (selenideElement.find(xpath("//div//a//i[contains(@class,'far fa-heart')]")).isDisplayed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addMessageToDatabase(int id, String text) {
        JDBCUtil.addMessage(id, text);
    }

    public void findUserPostAndClickedOnAuthor(String user) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        for (SelenideElement selenideElement : elements) {
            UtilService.waitHalfOfSecond();
            if (selenideElement.getText().contains(user)) {
                selenideElement.find(xpath("//a[contains(text(),'" + user + "')]")).click();
            }
        }
    }

    public boolean checkThatButtonNameIsUnsubscribe() {
        UtilService.waitHalfOfSecond();
        return $(xpath("//a[contains(text(),'Unsubscribe')]")).isDisplayed();
    }

    public void clickToSubscribersCount() {
        $(xpath("//div//h3//a[contains(@href,'/user/subscribers')]")).shouldBe(appear).click();
    }

    public boolean checkThatUserDisplayedInSubscribersList(String user) {
        UtilService.waitHalfOfSecond();
        return $(xpath("//ul//li//a[contains(text(),'" + user + "')]")).isDisplayed();
    }

    public boolean checkThatButtonNameIsSubscribe() {
        UtilService.waitHalfOfSecond();
        return $(xpath("//a[contains(text(),'Subscribe')]")).isDisplayed();
    }

    public boolean checkThatUserNotDisplayedInSubscribersList(String user) {
        UtilService.waitHalfOfSecond();
        return !$(xpath("//ul//li//a[contains(text(),'" + user + "')]")).isDisplayed();
    }

    public boolean findMessagePostedByUserAndCheckThatEditButtonVisible(String user) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        for (SelenideElement selenideElement : elements) {
            UtilService.waitHalfOfSecond();
            if (selenideElement.getText().contains(user)) {
                return selenideElement.find(xpath("//a[contains(text(),'Edit [ADMIN PERM]')]")).isDisplayed();
            }
        }
        return false;
    }

    public boolean checkThatCountOfPagesAndPageActive(String count, String activePage) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'container mt-3')][1]//div" +
                "[contains(@class,'row')][1]//ul[1]//li[contains(@class,'page-item')]"));
        System.out.println(elements);
        UtilService.waitHalfOfSecond();
        return elements.size() - 1 == Integer.parseInt(count) &&
                $(xpath("//div[contains(@class,'container mt-3')][1]//div[contains(@class,'row')][1]//ul[1]//" +
                        "li[contains(@class,'page-item active')][1]")).getText().equals(activePage);
    }

    public void clickButton(String type, String number) {
        int numberPage = Integer.parseInt(number) + 1;
        switch (type) {
            case "page nav": {
                $(xpath("//div[contains(@class,'container mt-3')][1]//div[contains(@class,'row')][1]" +
                        "//ul[1]//li[contains(@class,'page-item')][" + numberPage + "]")).click();
                break;
            }
            case "post nav":{
                $(xpath("//div[contains(@class,'container mt-3')][1]" +
                        "//div[contains(@class,'row')][1]//ul[2]//li[contains(@class,'page-item')]" +
                        "//a[contains(text(),'"+number+"')]")).click();
                break;
            }
        }
    }

    public boolean checkThatPostsOnPageAndSettingCountOfPosts(String activeSettingCount, String countPosts) {
        UtilService.waitHalfOfSecond();
        ElementsCollection elements = $$(xpath("//div[contains(@class,'card my-3')]"));
        System.out.println(elements);
        UtilService.waitHalfOfSecond();
        return elements.size()==Integer.parseInt(countPosts) && $(xpath("//div[contains(@class,'container mt-3')][1]" +
                "//div[contains(@class,'row')][1]//ul[2]//li[contains(@class,'page-item active')][1]")).getText().equals(activeSettingCount);
    }
}
