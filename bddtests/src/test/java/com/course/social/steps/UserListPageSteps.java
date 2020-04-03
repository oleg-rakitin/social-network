package com.course.social.steps;

import com.course.social.services.UserListPageService;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class UserListPageSteps {

    @Steps
    private UserListPageService userListPageService;


    @When("find {string} user and click to edit")
    public void findUserAndClickToEdit(String user) {
        userListPageService.findUserAndClickToEdit(user);
    }

    @When("check that admin unchecked and check if")
    public void checkThatAdminUncheckedAndCheckIf() {
        assertTrue(userListPageService.checkThatAdminUncheckedAndCheckIf());
    }

    @Then("check that admin checked")
    public void checkThatAdminChecked() {
        assertTrue(userListPageService.checkThatAdminChecked());
    }
}
