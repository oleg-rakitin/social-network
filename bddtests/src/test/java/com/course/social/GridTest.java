package com.course.social;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/GridTest.feature")
public class GridTest {
}
