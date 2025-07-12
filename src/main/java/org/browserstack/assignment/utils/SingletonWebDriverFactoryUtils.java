package org.browserstack.assignment.utils;

import org.browserstack.assignment.exception.InvalidWebDriverException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SingletonWebDriverFactoryUtils {
    private static SingletonWebDriverFactoryUtils instance;
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static WebDriver getThreadLocalDriver() {
        return threadLocalDriver.get();
    }

    public static void setThreadLocalDriver(String browser) {
        threadLocalDriver.set(getInstance().getDriver(browser));
    }

    public static void quitDriverAndRemove() {
        getThreadLocalDriver().quit();
        threadLocalDriver.remove();
    }

    private SingletonWebDriverFactoryUtils() {}

    private synchronized static SingletonWebDriverFactoryUtils getInstance() {
        if (instance == null){
            return instance = new SingletonWebDriverFactoryUtils();
        }
        return instance;
    }

    private WebDriver getDriver(String browser){
        return switch (browser.toLowerCase()){
            case "chrome" -> new ChromeDriver();
            case "edge" -> new EdgeDriver();
            case "firefox" -> new FirefoxDriver();
            default -> throw new InvalidWebDriverException("Browser is not available");
        };
    }
}
