package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class DmitryOmTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void TestFillingTestBox() {
        driver.get("https://demoqa.com/");

        String name = "Dmitry QA";
        String email = "dmitry.qa@example.com";
        String currentAddress = "Warsaw, Poland";
        String permanentAddress = "Zurawia 16/20, Warszawa, 00-515";

        wait.until(visibilityOfElementLocated(By.xpath("//h5[text()='Elements']"))).click();
        wait.until(visibilityOfElementLocated(By.xpath("//span[text()='Text Box']"))).click();
        wait.until(visibilityOfElementLocated(By.id("userName"))).sendKeys(name);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("currentAddress")).sendKeys(currentAddress);
        driver.findElement(By.id("permanentAddress")).sendKeys(permanentAddress);
        driver.findElement(By.id("submit")).click();

        WebElement outputBox = wait.until(visibilityOfElementLocated(By.id("output")));
        String outputAddress = outputBox.getText();

        Assert.assertTrue(outputAddress.contains(name), "Full name is missing");
        Assert.assertTrue(outputAddress.contains(email), "Email is missing");
        Assert.assertTrue(outputAddress.contains(currentAddress), "Current address is missing");
        Assert.assertTrue(outputAddress.contains(permanentAddress), "Permanent address is missing");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
