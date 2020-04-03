package com.course.social.services;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class UtilService {
    private final static Integer TIME_TO_WAIT = 10_000;

    public static void waitSomeTime(Supplier<Boolean> p) {
        Exception exc = null;
        int error = 0;
        do {
            try {
                Thread.sleep(100);
                error += 100;
                if (error > TIME_TO_WAIT) {
                    break;
                }
                exc = null;
            } catch (Exception e) {
                exc = e;
            }
        } while (p.get());
        if (exc != null) {
            throw new RuntimeException(exc.toString());
        }
    }

    public static void waitSomeTime() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitOneHundred() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitHalfOfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitInitBody() {
        waitSomeTime(() -> !($(xpath("//body[@class='app-initialization-compleate']")).isDisplayed()));
    }

    public static void waitPreloader() {
        waitHalfOfSecond();
        waitOneHundred();
        waitSomeTime(() -> $(id("globalHttpLoader")).find(id("spinner")).isDisplayed());
    }

    public static boolean isClickable(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(Serenity.getWebdriverManager().getCurrentDriver(), 4);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Useful method to visually locate given element on the page
     * Can be used for the debugging purposes
     *
     * @param element to be highlighted
     */
    public static void highlightElement(SelenideElement element) {
        String originalStyle = element.attr("style");
        applyStyle(element, "background: yellow; border: 2px solid red;");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        applyStyle(element, originalStyle);
    }

    private static void applyStyle(SelenideElement element, String style) {
        Selenide.executeJavaScript("arguments[0].setAttribute('style', arguments[1]);", element, style);
    }
}

