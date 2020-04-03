package com.course.social.services;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class UserListPageService {

    public void findUserAndClickToEdit(String user) {
        if ($(xpath("//table//tbody//tr[2]//td[1]")).getText().equals(user)) {
            $(xpath("//table//tbody//tr[2]//td[3]")).click();
        }
    }

    public boolean checkThatAdminUncheckedAndCheckIf() {
        SelenideElement element = $(xpath("//div//label//input['Admin']"));
        if (element.getAttribute("checked") != null) {
            element.click();
            $(xpath("//button[contains(text(),'Save')]")).click();
            return false;
        } else {
            element.click();
            $(xpath("//button[contains(text(),'Save')]")).click();
            return true;
        }
    }

    public boolean checkThatAdminChecked() {
        SelenideElement element = $(xpath("//div//label//input['Admin']"));
        return element.getAttribute("checked") != null;
    }
}
