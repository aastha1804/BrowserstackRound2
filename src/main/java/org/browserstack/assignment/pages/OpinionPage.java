package org.browserstack.assignment.pages;

import org.browserstack.assignment.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OpinionPage extends BasePage {

    @FindBy(xpath = "(//*[@id='main-content']//section[1]//article//header/h2/a)[position() <= 5]")
    private List<WebElement> topFiveArticleLinks;

    @FindBy(xpath = "//section//article//header//h2//a")
    private List<WebElement> allArticleLinks;

    public OpinionPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getFirstFiveArticleLinks() {
        return topFiveArticleLinks.stream()
                .map(link -> getAttribute(link, "href"))
                .collect(Collectors.toList());
    }

    public List<String> getAllOpinionArticleLinks() {
        return allArticleLinks.stream()
                .map(link -> getAttribute(link, "href"))
                .filter(url -> url != null && !url.isBlank())
                .collect(Collectors.toList());
    }


}
