package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class VladimirIzTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    public void testSaucedemoRegistrationUser() {
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(USERNAME);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(PASSWORD);

        driver.findElement(By.id("login-button")).click();
        WebElement actualResult = driver.findElement(By.xpath("//div[@class='app_logo']"));
        String value = actualResult.getText();

        Assert.assertEquals(value, "Swag Labs");
    }

    @Test
    public void testDelaysLiftoff() {
        driver.get("https://practice-automation.com/");

        driver.findElement(By.xpath("//div/a[@href='https://practice-automation.com/javascript-delays/']")).click();
        driver.findElement(By.xpath("//b")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(11));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("delay"), "Liftoff!"));

        String actualText = driver.findElement(By.id("delay")).getText();
        Assert.assertEquals(actualText, "Liftoff!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
