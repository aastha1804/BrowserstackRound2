package org.browserstack.assignment.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import lombok.extern.log4j.Log4j2;
import org.browserstack.assignment.base.BaseStep;
import org.browserstack.assignment.pages.ArticlePage;
import org.browserstack.assignment.pages.HomePage;
import org.browserstack.assignment.pages.OpinionPage;
import org.browserstack.assignment.utils.ConfigReader;
import org.browserstack.assignment.utils.TranslatorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class TranslateSteps extends BaseStep {

    private HomePage homePage;
    private OpinionPage opinionPage;
    protected List<String> spanishTitles;
    protected List<String> translatedTitles;
    private Map<String, Integer> wordFrequency;

    @Given("original article titles are collected in Spanish")
    public void originalArticleTitlesAreCollectedInSpanish() {
        log.info("Opening homepage and collecting Spanish titles");
        driver.get(ConfigReader.get("website-url", "https://elpais.com/"));
        homePage = new HomePage(driver);
        opinionPage = homePage.clickOpinionSection();

        spanishTitles = new ArrayList<>();
        List<String> articleLinks = opinionPage.getAllOpinionArticleLinks();

        for (String link : articleLinks) {
            try {
                driver.get(link);
                ArticlePage articlePage = new ArticlePage(driver);
                String title = articlePage.getArticleTitle();
                if (title != null && !title.isBlank()) {
                    spanishTitles.add(title);
                }
            } catch (Exception e) {
                log.warn("Skipping article due to error: " + e.getMessage());
            }
        }

        log.info("Collected " + spanishTitles.size() + " Spanish titles");
    }

    @When("the titles are translated using a translation API")
    public void theTitlesAreTranslatedUsingATranslationAPI() {
        translatedTitles = new ArrayList<>();
        for (String title : spanishTitles) {
            String translated = TranslatorUtil.translateToEnglish(title);
            translatedTitles.add(translated);
        }
    }

    @Then("the translated titles should be printed in the console")
    public void theTranslatedTitlesShouldBePrintedInTheConsole() {
        log.info("Printing Spanish and English titles:");

        for (int i = 0; i < spanishTitles.size(); i++) {
            System.out.println("--------------------------------------------------");
            System.out.println("Title " + (i + 1) + " (Original - Spanish): " + spanishTitles.get(i));
            System.out.println("Title " + (i + 1) + " (Translated - English): " + translatedTitles.get(i));
        }
        System.out.println("--------------------------------------------------");
    }

    @Given("translated titles are available")
    public void translatedTitlesAreAvailable() {
        if (translatedTitles == null || translatedTitles.isEmpty()) {
            throw new RuntimeException("No translated titles found. Run translation step first.");
        }
        System.out.println("Analyzing " + translatedTitles.size() + " translated titles...");
    }

    @When("words are extracted and counted")
    public void wordsAreExtractedAndCounted() {
        wordFrequency = new HashMap<>();
        for (String title : translatedTitles) {
            String[] words = title
                    .toLowerCase()
                    .replaceAll("[^a-z ]", "")
                    .split("\\s+");

            for (String word : words) {
                if (word.isBlank()) continue;
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }
    }

    @Then("words appearing more than twice should be printed with their frequencies")
    public void wordsAppearingMoreThanTwiceShouldBePrintedWithTheirFrequencies() {
        System.out.println("\nRepeated Words (More than 2 times):");
        boolean found = false;

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No words repeated more than twice.");
        }
    }
}

