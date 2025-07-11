package org.browserstack.assignment.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.browserstack.assignment.base.BaseStep;

import java.util.*;

public class AnalyzeSteps extends BaseStep {

    private List<String> translatedTitles;
    private Map<String, Integer> wordFrequency;

    @Given("translated titles are available")
    public void translatedTitlesAreAvailable() {
        translatedTitles = Arrays.asList(
                "Stop Putin and rebuild Ukraine",
                "The rights of the mind",
                "Rules for journalists to disturb",
                "Srebrenica we were there and it did not help",
                "Dead times",
                "Unemployed youth and democracy",
                "War or catastrophe",
                "We must rethink mental health",
                "The democracy of the credulous",
                "The world is burning and we look the other way",
                "Elections in a country that no longer exists",
                "Against the tiredness of politics",
                "Politics to the limit",
                "Ethics of responsibility",
                "There is no planet B",
                "Bridges or walls",
                "Citizenship and rights",
                "Fear is a bad adviser",
                "We have to talk about Europe",
                "Voices from the South",
                "The strength of the fragile"
        );

        System.out.println("Translated Titles:");
        translatedTitles.forEach(System.out::println);
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
    public void wordsAppearingMoreThanTwiceShouldBePrinted() {
        System.out.println("\n Repeated Words (More than 2 times):");
        boolean found = false;

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println(" No words repeated more than twice.");
        }
    }
}
