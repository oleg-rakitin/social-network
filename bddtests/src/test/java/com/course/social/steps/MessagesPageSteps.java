package com.course.social.steps;

import com.course.social.services.MessagesPagesService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class MessagesPageSteps {

    @Steps
    private MessagesPagesService messagesPagesService;


    @When("find message posted by {string} user and like")
    public void findMessagePostedByUserAndClickLike(String user) {
        messagesPagesService.findMessagePostedByUserAndClickLike(user);
    }

    @Then("check that post by {string} user liked")
    public void checkThatPostByUserLiked(String user) {
        assertTrue(messagesPagesService.checkThatPostByUserLiked(user));
    }

    @When("find message posted by {string} user and dislike")
    public void findMessagePostedByUserAndDislike(String user) {
        messagesPagesService.findMessagePostedByUserAndDislike(user);
    }

    @Then("check that post by {string} user disliked")
    public void checkThatPostByUserDisliked(String user) {
        assertTrue(messagesPagesService.checkThatPostByUserDisliked(user));
    }

    @Given("add message to database with id {string} and text {string}")
    public void addMessageToDatabase(String id, String text) {
        messagesPagesService.addMessageToDatabase(Integer.parseInt(id), text);
    }

    @When("find {string} user post and clicked on author")
    public void findUserPostAndClickedOnAuthor(String user) {
        messagesPagesService.findUserPostAndClickedOnAuthor(user);
    }

    @Then("check that button name is unsubscribe")
    public void checkThatButtonNameIsUnsubscribe() {
        assertTrue(messagesPagesService.checkThatButtonNameIsUnsubscribe());
    }

    @When("click to subscribers count")
    public void clickToSubscribersCount() {
        messagesPagesService.clickToSubscribersCount();
    }

    @Then("check that user {string} displayed in subscribers list")
    public void checkThatUserDisplayedInSubscribersList(String user) {
        assertTrue(messagesPagesService.checkThatUserDisplayedInSubscribersList(user));
    }

    @Then("check that button name is subscribe")
    public void checkThatButtonNameIsSubscribe() {
        assertTrue(messagesPagesService.checkThatButtonNameIsSubscribe());
    }

    @Then("check that user {string} not displayed in subscribers list")
    public void checkThatUserNotDisplayedInSubscribersList(String user) {
        assertTrue(messagesPagesService.checkThatUserNotDisplayedInSubscribersList(user));
    }

    @Then("find message posted by {string} user and check that edit button visible")
    public void findMessagePostedByUserAndCheckThatEditButtonVisible(String user) {
        assertTrue(messagesPagesService.findMessagePostedByUserAndCheckThatEditButtonVisible(user));
    }

    @Then("check that count of pages {string} and page {string} active")
    public void checkThatCountOfPagesAndPageActive(String count, String activePage) {
        assertTrue(messagesPagesService.checkThatCountOfPagesAndPageActive(count,activePage));
    }

    @When("click {string} {string} button")
    public void clickButton(String number, String type) {
        messagesPagesService.clickButton(type,number);
    }

    @Then("check that posts on page {string} and setting count of posts {string}")
    public void checkThatPostsOnPageAndSettingCountOfPosts(String countPosts, String activeSettingCount) {
        assertTrue(messagesPagesService.checkThatPostsOnPageAndSettingCountOfPosts(activeSettingCount,countPosts));
    }
}
