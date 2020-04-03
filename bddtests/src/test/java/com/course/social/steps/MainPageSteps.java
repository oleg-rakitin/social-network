package com.course.social.steps;

import com.course.social.services.MainPageService;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class MainPageSteps {

    @Steps
    private MainPageService mainPageService;

    @When("open main page")
    public void whenOpenMainPage() {
        mainPageService.openMainPage();
    }

    @When("check that {string} page opened")
    public void checkThatPageOpened(String page) {
        assertTrue(mainPageService.checkThatPageOpened(page));
    }

    @When("click to {string} button")
    public void clickToButton(String arg0) {
        mainPageService.clickToButton(arg0);
    }

    @When("refresh {string} page")
    public void refreshPage(String arg0) {
        mainPageService.refreshPage(arg0);
    }

    @When("send {string} to {string} input")
    public void sendToInput(String data, String input) {
        mainPageService.sendToInput(input,data);
    }

    @When("check recaptcha")
    public void checkRecaptcha() {
        mainPageService.checkRecaptcha();
    }

    @When("go to {string} page")
    public void goToPage(String page) {
        mainPageService.goToPage(page);
    }

    @Then("check that message added")
    public void checkThatMessageAdded() {
        assertTrue(mainPageService.checkThatMessageAdded());
    }

    @When("logout")
    public void logout() {
        mainPageService.logout();
    }
}
