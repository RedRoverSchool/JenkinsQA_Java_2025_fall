package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class YaroslavaTest {
    private WebDriver driver;
  private WebDriverWait wait;
  private static final String USERNAME = "yar"+ System.currentTimeMillis();
 private static final String USEREMAIL = USERNAME + "@gmail.com";

    @BeforeMethod
    public void startBeforeTest() {
        driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }





    @Test
    public void testLogIn() throws InterruptedException {
        Thread.sleep(100);
        WebElement signUplogInButton = driver.findElement(By.xpath("//a[@href='/login']"));
        signUplogInButton.click();

        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//form//input[@data-qa='signup-name']")));
        loginField.sendKeys(USERNAME);

        WebElement userPassword = driver.findElement(By.xpath("//form//input[@data-qa='signup-email']"));
        userPassword.sendKeys(USEREMAIL);

        WebElement signUpButton = driver.findElement(By.xpath("//form//button[@data-qa='signup-button']"));
                signUpButton.click();

        WebElement createButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//form//button[@data-qa='create-account']")));

        Assert.assertEquals(createButton.getText(),"Create Account");
    }

    @AfterMethod
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
