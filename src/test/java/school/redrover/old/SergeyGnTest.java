package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Ignore
public class SergeyGnTest {

    private static final String LOGIN_PAGE_URL = "https://www.saucedemo.com/";
    private static final String PRODUCTS_PAGE_URL = "https://www.saucedemo.com/inventory.html";

    private static final By USER_NAME_INPUT = By.xpath("//input[@data-test='username']");
    private static final By PASSWORD_INPUT = By.xpath("//input[@data-test='password']");
    private static final By LOGIN_BUTTON = By.xpath("//input[@data-test='login-button']");
    private static final By ERROR = By.xpath("//h3[@data-test='error']");

    private static final Duration TIMEOUT = Duration.ofMillis(500);

    WebDriver driver;

    @BeforeMethod
    private void initDriver() {
        driver = new ChromeDriver();
        driver.get(LOGIN_PAGE_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(TIMEOUT);
    }

    @Test
    public void testSuccessLogin() {
        login("standard_user", "secret_sauce");
        assertEquals(driver.getCurrentUrl(), PRODUCTS_PAGE_URL);
    }

    private void login(String username, String password) {
        driver.findElement(USER_NAME_INPUT).sendKeys(username);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }

    @Test
    public void testEmptyFieldsLogin() {
        driver.findElement(LOGIN_BUTTON).click();
        driver.manage().timeouts().implicitlyWait(TIMEOUT);

        String errorText = driver.findElement(ERROR).getText();
        assertEquals(errorText, "Epic sadface: Username is required");
    }

    @Test
    public void testCorrectUserNameAndEmptyPasswordFieldLogin() {
        driver.findElement(USER_NAME_INPUT).sendKeys("standard_user");
        driver.findElement(LOGIN_BUTTON).click();
        driver.manage().timeouts().implicitlyWait(TIMEOUT);

        String errorText = driver.findElement(ERROR).getText();

        assertEquals(errorText, "Epic sadface: Password is required");
    }

    @AfterMethod
    private void tearDown() {
        if (driver != null) driver.quit();
    }
}
