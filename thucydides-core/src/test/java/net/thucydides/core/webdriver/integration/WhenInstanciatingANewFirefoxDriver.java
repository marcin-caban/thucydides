package net.thucydides.core.webdriver.integration;

import net.thucydides.core.webdriver.SupportedWebDriver;
import net.thucydides.core.webdriver.WebDriverFactory;
import net.thucydides.core.webdriver.WebdriverInstanceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class WhenInstanciatingANewFirefoxDriver {

    WebdriverInstanceFactory webdriverInstanceFactory;

    WebDriver driver;

    @Before
    public void createFactory() {
        webdriverInstanceFactory = new WebdriverInstanceFactory();
    }

    @After
    public void closeFirefox() {
        driver.quit();
    }

    @Test
    public void should_support_creating_a_firefox_driver() throws Exception {
         driver = webdriverInstanceFactory.newInstanceOf(FirefoxDriver.class);
         assertThat(driver, instanceOf(FirefoxDriver.class));
    }

    @Test
    public void should_support_creating_a_firefox_driver_with_a_profile() throws Exception {
        FirefoxProfile profile = new FirefoxProfile();
        driver = webdriverInstanceFactory.newInstanceOf(FirefoxDriver.class, profile);
        assertThat(driver, instanceOf(FirefoxDriver.class));
    }
}