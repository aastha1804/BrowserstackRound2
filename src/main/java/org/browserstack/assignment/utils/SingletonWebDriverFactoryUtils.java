package org.browserstack.assignment.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.browserstack.assignment.exception.InvalidWebDriverException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

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

    private WebDriver getDriver(String browserOrEnv) {
        String envType = ConfigReader.get("environment","chrome");

        if ("browserstack".equalsIgnoreCase(envType)) {
            return createBrowserStackDriver(browserOrEnv);
        } else {
            return switch (browserOrEnv.toLowerCase()) {
                case "chrome" -> new ChromeDriver();
                case "firefox" -> new FirefoxDriver();
                case "edge" -> new EdgeDriver();
                default -> throw new InvalidWebDriverException("Invalid local browser: " + browserOrEnv);
            };
        }
    }

    public static WebDriver createBrowserStackDriver(String envIdentifier) {
        try {
            JsonNode config = BrowserStackConfigReader.readConfig();
            JsonNode environments = config.get("environments");

            JsonNode targetEnv;
            if (envIdentifier.startsWith("browser_")) {
                int index = Integer.parseInt(envIdentifier.split("_")[1]);
                targetEnv = environments.get(index);
            } else {
                targetEnv = findEnvironmentByDevice(environments, envIdentifier);
            }

            MutableCapabilities caps = new MutableCapabilities();
            MutableCapabilities bstackOptions = new MutableCapabilities();

            if (targetEnv.has("browser")) {
                caps.setCapability("browserName", targetEnv.get("browser").asText());
                caps.setCapability("browserVersion", targetEnv.get("browser_version").asText());
                bstackOptions.setCapability("os", targetEnv.get("os").asText());
                bstackOptions.setCapability("osVersion", targetEnv.get("os_version").asText());
            } else {
                bstackOptions.setCapability("deviceName", targetEnv.get("device").asText());
                bstackOptions.setCapability("osVersion", targetEnv.get("os_version").asText());
                bstackOptions.setCapability("realMobile", "true");
            }

            JsonNode capsNode = config.get("capabilities");
            bstackOptions.setCapability("projectName", capsNode.get("project").asText());
            bstackOptions.setCapability("buildName", capsNode.get("build").asText());
            bstackOptions.setCapability("sessionName", capsNode.get("name").asText());

            caps.setCapability("bstack:options", bstackOptions);

            String username = config.get("username").asText();
            String accessKey = config.get("access_key").asText();
            String hubUrl = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

            return new RemoteWebDriver(new URL(hubUrl), caps);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create BrowserStack driver: " + e.getMessage(), e);
        }
    }

    private static JsonNode findEnvironmentByDevice(JsonNode environments, String deviceName) {
        for (JsonNode env : environments) {
            if (env.has("device") && env.get("device").asText().equalsIgnoreCase(deviceName)) {
                return env;
            }
        }
        throw new RuntimeException("Device " + deviceName + " not found in browserstack-config.json");
    }
}