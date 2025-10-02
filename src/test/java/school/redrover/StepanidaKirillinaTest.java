package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String PASSWORD = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testHomePageTitle() {
        waitForLogoToBeVisible();

        Assert.assertEquals(driver.getTitle(), "Swag Labs");
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

        Assert.assertEquals(driver.findElement(By.className("app_logo")).getText(), "Swag Labs");
    }

    private WebDriverWait getWait5() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void waitForLogoToBeVisible() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("login_logo")));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
