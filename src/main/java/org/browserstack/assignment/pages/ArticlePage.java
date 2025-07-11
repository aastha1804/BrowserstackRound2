package org.browserstack.assignment.pages;

import org.browserstack.assignment.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class ArticlePage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement articleTitle;

    @FindBy(xpath = "//*[@id=\"main-content\"]/div[2]/p[1]")
    private WebElement articleContent;

    @FindBy(xpath = "//*[@id=\"main-content\"]/header/div[2]/figure/span/img")
    private WebElement coverImage;

    public ArticlePage(WebDriver driver){
        super(driver);
    }

    public String getArticleTitle() {
        return articleTitle.getText().trim();
    }

    public String getArticleContent() {
        try {
            return articleContent.getText().trim();
        } catch (Exception e) {
            return "[SKIPPED: content not available]";
        }
    }

    public String getCoverImageUrl() {
        try {
            return coverImage.getAttribute("src");
        } catch (Exception e) {
            return null;
        }
    }

}
