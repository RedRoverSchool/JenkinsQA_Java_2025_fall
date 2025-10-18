package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Logger;

public class GroupCodeCoffeeJavaTest {
    protected WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    private static final Logger logger = Logger.getGlobal();
    private int time;
    WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(4));

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://demoqa.com");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.isSuccess()) {
            System.out.println("✅ Test PASSED: " + result.getName());
            logger.info(String.format("✅ Test PASSED: %s", result.getName()));

        } else if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("❌ Test FAILED: " + result.getName());
            logger.warning(String.format("❌ Test FAILED: %s", result.getName()));

        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("⚠️ Test SKIPPED: " + result.getName());
            logger.warning(String.format("⚠️ Test SKIPPED: %s", result.getName()));
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testDemoQa() throws InterruptedException {
        WebElement elements = driver.findElement(By.xpath("(//div[@class = 'card-up'])[1]"));
        wait4.until(ExpectedConditions.visibilityOf(elements)).click();

        WebElement textBox = driver.findElement(By.xpath("(//ul[@class='menu-list'])[1]//li[1]"));
        wait4.until(ExpectedConditions.visibilityOf(textBox)).click();

        WebElement fullName = driver.findElement(By.id("userName"));
        fullName.sendKeys("Алиса Т.");

        WebElement email = driver.findElement(By.cssSelector("#userEmail"));
        email.sendKeys("Alisya152@gmail.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        WebElement resultName = driver.findElement(By.cssSelector("#name"));
        WebElement resultEmail = driver.findElement(By.cssSelector("#email"));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // Проверяем правильность текста
        Assert.assertEquals(resultName.getText(), "Name:Алиса Т.");
        Assert.assertEquals(resultEmail.getText(), "Email:Alisya152@gmail.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }
}
