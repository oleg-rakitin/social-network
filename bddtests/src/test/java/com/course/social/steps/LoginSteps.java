package com.course.social.steps;

import com.course.social.services.LoginPageService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    @Steps
    private LoginPageService loginPage;

    @Given("^get login by \"([^\"]*)\"$")
    public void getLogin(String userType) {
        loginPage.login(userType);
    }

    @When("check that auth success")
    public void checkThatAuthSuccess() {
        assertTrue(loginPage.checkThatAuthSuccess());
    }

    @Given("clear database")
    public void clearDatabase() {
        loginPage.clearDatabase();
    }

    @Given("get login with username {string} and password {string}")
    public void getLoginWithUsernameAndPassword(String username, String password) {
        loginPage.login(username,password);
    }

    @Then("check that auth not success")
    public void checkThatAuthNotSuccess() {
        assertTrue(loginPage.checkThatAuthNotSuccess());
    }

    //@When("^logout$")
    //public void getLogout() {
    //  loginPage.logout();
    // }

    //@Given("^get login in keycloak$")
    // public void getLoginInKeyCloak() {
    //   loginPage.loginInKeyCloak();
    //  }
}
