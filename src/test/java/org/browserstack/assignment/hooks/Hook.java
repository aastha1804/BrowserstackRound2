package org.browserstack.assignment.hooks;

import org.browserstack.assignment.base.utils.ScreenShotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import static org.browserstack.assignment.base.utils.SingletonWebDriverFactoryUtils.*;

@Log4j2
public class Hook {
    @Getter
    private static String browserName;

    @Before
    public void setUp() {
        try{
            browserName=System.getProperty("browser", "chrome");
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
