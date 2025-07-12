package org.browserstack.assignment.base;

import org.browserstack.assignment.base.utils.PropertiesUtil;
import lombok.Data;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

import static org.browserstack.assignment.utils.SingletonWebDriverFactoryUtils.getThreadLocalDriver;


@Data
public class BaseStep {
    protected WebDriver driver;
    protected Properties properties;

    public BaseStep() {
        this.driver = getThreadLocalDriver();
        this.properties = PropertiesUtil.getProperties();
    }
}
