package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Collections;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return determineDriver();
    }

    private static WebDriver determineDriver() {

        if (driver == null) {

            String browserName = getBrowser();
            String os = getOS();

            if (browserName.equalsIgnoreCase("chrome")) {
                if (os.contains("windows")) {
                    System.setProperty("webdriver.chrome.driver", ".//bin//selenium//chrome//chromedriver.exe");
                } else if (os.contains("mac")) {
                    System.setProperty("webdriver.chrome.driver", "bin//selenium//chrome//chromedriver");
                } else {
                    //Currently defaults to run in mac os, we could either throw an exception here or implement for other OS
                    System.setProperty("webdriver.chrome.driver", "bin//selenium//chrome//chromedriver");
                }

                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

                driver = new ChromeDriver(options);

            } else if (browserName.equalsIgnoreCase("firefox")) {
                //TODO: need to provide webdriver.gecko.driver with the path of the gecko driver, same as chrome driver
                driver = new FirefoxDriver();
            } else {
                throw new RuntimeException("Invalid browser name");
            }
        }
        return driver;
    }


    private static String getBrowser() {
        return System.getProperty("browser.value") != null ? System.getProperty("browser.value") : "chrome";
    }

    private static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

}
