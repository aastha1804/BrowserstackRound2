Feature: Automate El País Opinion Articles Workflow
  Background:
    Given the El País homepage is opened

  @run_this
  Scenario: Scrape top 5 opinion articles
    When the user navigates to the Opinion section
    And the top 5 articles are extracted with titles and content
    And cover images are downloaded if available

  Scenario: Translate article titles to English
    Given original article titles are collected in Spanish
    When the titles are translated using a translation API
    Then the translated titles should be printed in the console

  Scenario: Analyze repeated words in translated titles
    Given translated titles are available
    When words are extracted and counted
    Then words appearing more than twice should be printed with their frequencies

  @crossbrowser
  Scenario: Execute the solution in parallel on BrowserStack
    Given the scraping solution is validated locally
    When tests are executed in parallel on 5 BrowserStack environments
    Then the solution should complete successfully across all environments
