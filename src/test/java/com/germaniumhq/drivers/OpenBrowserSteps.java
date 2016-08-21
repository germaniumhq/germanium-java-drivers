package com.germaniumhq.drivers;

import com.germaniumhq.EnsureDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertTrue;

public class OpenBrowserSteps {
    private final static Logger log = Logger.getLogger(OpenBrowserSteps.class);

    @Given("^I open Firefox$")
    public void i_open_Firefox() throws Throwable {
        String driverPath = EnsureDriver.ensureDriver("firefox");

        DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
        firefoxCapabilities.setCapability(FirefoxDriver.BINARY, driverPath);

        driver(new FirefoxDriver(firefoxCapabilities));
    }

    @Given("^I go to google$")
    public void i_go_to_google() throws Throwable {
        driver().get("http://google.com");
    }

    @Then("^the title is \"([^\"]*)\"$")
    public void the_title_is(String arg1) throws Throwable {
        assertTrue(driver().getTitle().contains(arg1));
    }

    @Given("^I open Chrome$")
    public void i_open_Chrome() throws Throwable {
        String driverPath = EnsureDriver.ensureDriver("chrome");
        System.setProperty("webdriver.chrome.driver", driverPath);

        driver(new ChromeDriver(DesiredCapabilities.chrome()));
    }

    @Given("^I open IE$")
    public void i_open_IE() throws Throwable {
        EnsureDriver.ensureDriver("ie");
        driver(new InternetExplorerDriver());
    }

    private WebDriver driver(WebDriver webDriver) {
        Context.set("driver", webDriver);

        return webDriver;
    }

    private WebDriver driver() {
        return Context.get("driver");
    }
}
