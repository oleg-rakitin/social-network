package com.course.social.services;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class ProfilePageService {

    public boolean checkThatPasswordNotChanged() {
        return $(xpath("//div//input[contains(@name,'oldPassword')]")).getAttribute("is-invalid") != null;
    }

    public boolean checkThatInputContainsData(String input, String data) {
        UtilService.waitHalfOfSecond();
        switch (input){
            case "email":{
                return $(xpath("//input[contains(@name,'email')]")).getAttribute("value").equals(data);
            }
        }
        return false;
    }
}
