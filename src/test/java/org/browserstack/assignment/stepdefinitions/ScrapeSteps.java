package org.browserstack.assignment.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.browserstack.assignment.base.BaseStep;
import org.browserstack.assignment.pages.HomePage;
import org.browserstack.assignment.utils.ConfigReader;

@Log4j2
public class ScrapeSteps extends BaseStep {

    private HomePage homePage;

    @Given("the El País homepage is opened")
    public void theElPaísHomepageIsOpened() {
        try {
            log.info("Opening El País homepage");
            driver.get(ConfigReader.get("website-url", "https://elpais.com/"));
            homePage = new HomePage(driver);
            log.info("Homepage loaded and HomePage object created");
        } catch (Exception e) {
            log.error("Failed to open El País homepage: {}", e.getMessage());
            throw new RuntimeException("Failed to open El País homepage", e);
        }
    }

    @When("the user navigates to the Opinion section")
    public void theUserNavigatesToTheOpinionSection() {
        try {
            log.info("Navigating to the Opinion section");
            homePage.clickOpinionSection();
        } catch (Exception e) {
            log.error("Failed to navigate to Opinion section: {}", e.getMessage());
            throw new RuntimeException("Failed to navigate to Opinion section", e);
        }
    }

    @And("the top {int} articles are extracted with titles and content")
    public void theTopArticlesAreExtractedWithTitlesAndContent(int count) {
        /*log.info("Extracting top " + count + " articles");

        articleLinks = opinionPage.getFirstFiveArticleLinks();
        log.debug("Found " + articleLinks.size() + " article links");

        for (int i = 0; i < Math.min(count, articleLinks.size()); i++) {
            String url = articleLinks.get(i);
            log.info("Navigating to article URL: " + url);
            driver.get(url);

            ArticlePage articlePage = new ArticlePage(driver);
            String title = articlePage.getArticleTitle();
            String content = articlePage.getArticleContent();

            log.debug("Article " + (i + 1) + " Title: " + title);
            log.debug("Article " + (i + 1) + " Content length: " + content.length());

            System.out.println("Article " + (i + 1) + ":");
            System.out.println("Title: " + title);
            System.out.println("Content: " + content);
            System.out.println("--------------------------------------------------");
        }*/
    }

    @And("cover images are downloaded if available")
    public void coverImagesAreDownloadedIfAvailable() {
        /*log.info("Downloading cover images if available");

        if (articleLinks == null || articleLinks.isEmpty()) {
            articleLinks = opinionPage.getFirstFiveArticleLinks();
        }

        for (int i = 0; i < articleLinks.size(); i++) {
            String url = articleLinks.get(i);
            log.info("Navigating to article " + (i + 1) + " for image download: " + url);
            driver.get(url);

            ArticlePage articlePage = new ArticlePage(driver);
            String imageUrl = articlePage.getCoverImageUrl();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                String filename = "article" + (i + 1) + "_cover.jpg";
                log.info("Downloading image for article " + (i + 1) + ": " + imageUrl);
                ImageDownloader.downloadImage(imageUrl, filename);
            } else {
                log.warn("No image found for article " + (i + 1));
            }
        }*/
    }
}
