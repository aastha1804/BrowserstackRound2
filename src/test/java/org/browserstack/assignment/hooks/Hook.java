package org.browserstack.assignment.hooks;

import org.browserstack.assignment.base.utils.ScreenShotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.browserstack.assignment.utils.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.browserstack.assignment.utils.SingletonWebDriverFactoryUtils.*;


@Log4j2
public class Hook {
    @Getter
    private static String browserName;

    @Before
    public void setUp() {
        try{
            browserName= ConfigReader.get("environment","chrome");
            setThreadLocalDriver(browserName);
            getThreadLocalDriver().manage().window().maximize();
            log.info("Setup Completed for : {}", getThreadLocalDriver().getClass().getSimpleName());
        }
        catch (Exception e){
            log.error("Error!!! Failed to setup WebDriver: {}", e.getMessage());
            throw e;
        }
    }

    @After
    @SneakyThrows
    public void tearDown(Scenario scenario) {
        try{
            log.info("TearDown invoked...");

            // Notify BrowserStack of pass/fail status
            WebDriver driver = getThreadLocalDriver();
            if (driver instanceof JavascriptExecutor js) {
                String status = scenario.isFailed() ? "failed" : "passed";
                String reason = scenario.getName();
                js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\""
                        + status + "\", \"reason\": \"" + reason + "\"}}");
            }

            if(scenario.isFailed()){
                log.error("Scenario Failed {}", scenario.getName());
                ScreenShotUtils.getScreenShot(getThreadLocalDriver(), scenario.getName()+this.getClass().getName());
                quitDriverAndRemove();
            }
            else {
                if (getThreadLocalDriver() != null) {
                    log.info("Quitting WebDriver...");
                    quitDriverAndRemove();
                }
            }
        }
        catch (Exception e){
            log.error("Error!!! Failed to quit WebDriver: {}", e.getMessage());
            throw e;
        }
    }
}
