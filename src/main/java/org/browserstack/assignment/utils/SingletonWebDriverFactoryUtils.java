package org.browserstack.assignment.utils;

import org.browserstack.assignment.exception.InvalidWebDriverException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;

public class SingletonWebDriverFactoryUtils {
    private static SingletonWebDriverFactoryUtils instance;
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static WebDriver getThreadLocalDriver() {
        return threadLocalDriver.get();
    }

    public static void setThreadLocalDriver(String browser) {
        String envType = ConfigReader.get("environment", "chrome");

        if ("browserstack".equalsIgnoreCase(envType)) {
            try {
                String browserIdentifier = ConfigReader.get("browser", "browser_0");
                System.out.println("Launching BrowserStack with: " + browserIdentifier);

                MutableCapabilities caps = new MutableCapabilities();
                // Minimal required BrowserStack capabilities
                caps.setCapability("browserName", "chrome"); // Override if needed
                caps.setCapability("bstack:options", Map.of(
                        "os", "Windows",
                        "osVersion", "10",
                        "projectName", "BrowserStack Samples",
                        "buildName", "browserstack build",
                        "sessionName", "Test Run"
                ));

                WebDriver driver = new RemoteWebDriver(
                        new URL("https://aasthasinha_9TAE7P:4YmtFCzpx3szz8H3zcNs@hub-cloud.browserstack.com/wd/hub"),
                        caps
                );

                threadLocalDriver.set(driver);
            } catch (Exception e) {
                throw new RuntimeException("BrowserStack session error: " + e.getMessage(), e);
            }
        } else {
            threadLocalDriver.set(getInstance().getDriver(browser));
        }
    }

    public static void quitDriverAndRemove() {
        if (getThreadLocalDriver() != null) {
            getThreadLocalDriver().quit();
            threadLocalDriver.remove();
        }
    }

    private SingletonWebDriverFactoryUtils() {}

    private synchronized static SingletonWebDriverFactoryUtils getInstance() {
        if (instance == null) {
            instance = new SingletonWebDriverFactoryUtils();
        }
        return instance;
    }

    private WebDriver getDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            default -> throw new InvalidWebDriverException("Invalid local browser: " + browser);
        };
    }
}
