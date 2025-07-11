package org.browserstack.assignment.pages;

import org.browserstack.assignment.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[text()='Opinión']")
    WebElement opinionLink;

    public HomePage(WebDriver driver){
        super(driver);
    }

    public OpinionPage clickOpinionSection() {
        acceptCookieConsent();
        clickButton(opinionLink);
        return new OpinionPage(driver);
    }
}
