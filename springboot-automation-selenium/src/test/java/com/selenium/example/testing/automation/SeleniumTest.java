package sample.selenium.example.test.automation;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import com.thoughtworks.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ThymeleafApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumTest {

    private WebDriver chromeDriver;
    private WebDriver firefoxDriver;
    private WebDriverManager chromewdm;
    private WebDriverManager firefoxwdm;
    private WebDriver chromeGridDriver;
    private WebDriver firefoxGridDriver;
    private WebDriver edgeGridDriver;
    private Selenium chromeSelenium;
    private DesiredCapabilities chromeCapability;
    private Selenium firefoxSelenium;
    private DesiredCapabilities firefoxCapability;
    private Selenium edgeSelenium;
    private DesiredCapabilities edgeCapability;


    @BeforeClass
    public static void start() {
        chromewdm = WebDriverManager.chromedriver().browserInDocker().enableVnc().enableRecording();
        firefoxwdm = WebDriverManager.chromedriver().browserInDocker().enableVnc().enableRecording();
        firefoxSelenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080");
        firefoxCapability = DesiredCapabilities.firefox();
        firefoxCapability.setBrowserName("firefox");
        firefoxCapability.setVersion(“4”);
        chromeSelenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080");
        chromeCapability = DesiredCapabilities.chrome();
        chromeCapability.setBrowserName("Chrome");
        edgeSelenium = new DefaultSelenium("localhost", 4444, "*edge", "http://localhost:8080");
        edgeCapability = DesiredCapabilities.edge();
        edgeCapability.setBrowserName("MicrosoftEdge");
        //System.setProperty("webdriver.chrome.driver", "path to webdriver");
        //System.setProperty("webdriver.gecko.driver", "path to webdriver");
        //System.setProperty("webdriver.edge.driver", "path to webdriver");
        //System.setProperty("webdriver.opera.driver", "path to webdriver");
        //System.setProperty("webdriver.ie.driver", "path to webdriver");
    }

    @Before
    public void setUp() {
        chromeDriver = chromewdm.create();
        firefoxDriver = firefoxwdm.create();
        firefoxGridDriver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"), capability);
        chromeGridDriver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"), capability);
        edgeGridDriver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"), capability);
    }

    @Test
    public void chromedrivertest() throws InterruptedException {
        chromeDriver.get("http://localhost:8080");

        Thread.sleep(1500);

        final WebElement dropdown = driver.findElement(By.linkText("Dropdown Test"));
        dropdown.click();

        Thread.sleep(1500);

        final WebElement choice2 = driver.findElement(By.linkText("Test 2"));
        choice2.click();

        Thread.sleep(1500);

        final String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).endsWith("/test/2");
    }

    @Test
    public void firefoxdrivertest() throws InterruptedException {
        firefoxDriver.get("http://localhost:8080");

        Thread.sleep(1500);

        final WebElement dropdown = driver.findElement(By.linkText("Dropdown Test"));
        dropdown.click();

        Thread.sleep(1500);

        final WebElement choice2 = driver.findElement(By.linkText("Test 2"));
        choice2.click();

        Thread.sleep(1500);

        final String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).endsWith("/test/2");
    }

    @Test
    public void firefoxgriddrivertest() throws InterruptedException {
        firefoxGridDriver.get("http://localhost:8080");

        Thread.sleep(1500);

        final WebElement dropdown = driver.findElement(By.linkText("Dropdown Test"));
        dropdown.click();

        Thread.sleep(1500);

        final WebElement choice2 = driver.findElement(By.linkText("Test 2"));
        choice2.click();

        Thread.sleep(1500);

        final String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).endsWith("/test/2");
    }


    @Test
    public void chromoegriddrivertest() throws InterruptedException {
        chromeGridDriver.get("http://localhost:8080");

        Thread.sleep(1500);

        final WebElement dropdown = driver.findElement(By.linkText("Dropdown Test"));
        dropdown.click();

        Thread.sleep(1500);

        final WebElement choice2 = driver.findElement(By.linkText("Test 2"));
        choice2.click();

        Thread.sleep(1500);

        final String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).endsWith("/test/2");
    }

    @Test
    public void edgegriddrivertest() throws InterruptedException {
        edgeGridDriver.get("http://localhost:8080");

        Thread.sleep(1500);

        final WebElement dropdown = driver.findElement(By.linkText("Dropdown Test"));
        dropdown.click();

        Thread.sleep(1500);

        final WebElement choice2 = driver.findElement(By.linkText("Test 2"));
        choice2.click();

        Thread.sleep(1500);

        final String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).endsWith("/test/2");
    }

    @After
    public void tearDown() {
        chromeDriver.quit();
        firefoxDriver.quit();
        firefoxGridDriver.quit();
        chromeGridDriver.quit();
        edgeGridDriver.quit();
    }
}
