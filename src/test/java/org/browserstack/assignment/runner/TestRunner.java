package org.browserstack.assignment.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@SuppressWarnings("all")
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.browserstack.assignment.stepdefinitions", "org.browserstack.assignment.hooks"},
        monochrome = true,// Enable readable console output
//        dryRun = true,
//        tags = "@run_this",
        plugin = {
                "pretty",                              // Pretty console output
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // Allure plugin
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
