package com.germaniumhq.drivers;

import com.germaniumhq.EnsureDriver;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertTrue;

public class OpenBrowserSteps {
    private final static Logger log = Logger.getLogger(OpenBrowserSteps.class);

    @After
    public void kill_browser() {
        driver().quit();
    }

    @Given("^I open Firefox$")
    public void i_open_Firefox() throws Throwable {
        String driverPath = EnsureDriver.ensureDriver("firefox");
        System.setProperty("webdriver.gecko.driver", driverPath);

        WebDriver webDriver = new FirefoxDriver();
        Context.set("driver", webDriver);
    }

    @Given("^I open Edge$")
    public void i_open_Edge() throws Throwable {
        String driverPath = EnsureDriver.ensureDriver("edge");

        WebDriver webDriver = new EdgeDriver();
        Context.set("driver", webDriver);
    }

    @Given("^I open Chrome$")
    public void i_open_Chrome() throws Throwable {
        String driverPath = EnsureDriver.ensureDriver("chrome");
        System.setProperty("webdriver.chrome.driver", driverPath);

        WebDriver webDriver = new ChromeDriver(DesiredCapabilities.chrome());
        Context.set("driver", webDriver);
    }

    @Given("^I open IE$")
    public void i_open_IE() throws Throwable {
        EnsureDriver.ensureDriver("ie");
        WebDriver webDriver = new InternetExplorerDriver();
        Context.set("driver", webDriver);
    }

    @Given("^I go to google$")
    public void i_go_to_google() throws Throwable {
        driver().get("http://google.com");
    }

    @Then("^the title is \"([^\"]*)\"$")
    public void the_title_is(String arg1) throws Throwable {
        assertTrue(driver().getTitle().contains(arg1));
    }

    private static WebDriver driver() {
        return Context.get("driver");
    }
}
