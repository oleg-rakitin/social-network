package com.course.social.steps;

import com.course.social.services.ProfilePageService;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class ProfilePageSteps {

    @Steps
    private ProfilePageService profilePageService;

    @When("")
    public void test(){

    }

    @Then("check that password not changed")
    public void checkThatPasswordNotChanged() {
        assertTrue(profilePageService.checkThatPasswordNotChanged());
    }

    @Then("check that {string} input contains {string} data")
    public void checkThatInputContainsData(String input, String data) {
        assertTrue(profilePageService.checkThatInputContainsData(input,data));
    }
}
