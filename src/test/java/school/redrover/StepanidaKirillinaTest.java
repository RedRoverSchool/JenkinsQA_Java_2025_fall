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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class StepanidaKirillinaTest {
    private WebDriver driver;
    private static final String PASSWORD = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageTitle() {
        waitForLogoToBeVisible();

        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, "Swag Labs");
    }

    @DataProvider(name = "userCredentials")
    public Object[][] provideUserCredentials() {
        return new Object[][]{
                {"standard_user", PASSWORD},
                {"performance_glitch_user", PASSWORD},
                {"visual_user", PASSWORD}
        };
    }

    @Test(dataProvider = "userCredentials")
    public void testSuccessfulLogin(String username, String password) {
        waitForLogoToBeVisible();

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='title']")));

        WebElement appLogo = driver.findElement(By.className("app_logo"));

        Assert.assertEquals(appLogo.getText(), "Swag Labs");
    }

    private WebDriverWait getWait5() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void waitForLogoToBeVisible() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("login_logo")));
    }
}
