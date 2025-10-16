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

public class BugSharksGroupTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String PASSWORD = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void automationexercise(){
        driver.get("https://automationexercise.com/");
    }

    @Test
    public void testSuccessfulLoginPos() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//h6[text()='Dashboard']")).getText(),
                "Dashboard");
    }

    @Test
    public void testWrongCredentialsLoginNeg() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("wrongLogin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("wrongPassword");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//p[text()='Invalid credentials']"))
                .getText(), "Invalid credentials");
    }

    @Test
    public void testEmptyCredentialsLoginNeg() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By
                        .xpath("//label[text()='Username']/ancestor::div/span[text()='Required']")).getText(),
                "Required");
        Assert.assertEquals(driver.findElement(By
                        .xpath("//label[text()='Password']/ancestor::div/span[text()='Required']")).getText(),
                "Required");
    }

    @Test
    public void testSauceDemoHomePageTitle() {
        openSauceDemoHomePage();

        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, "Swag Labs");
    }

    @DataProvider(name = "userCredentials")
    public Object[][] provideUserCredentials() {
        return new Object[][]{
                {"standard_user", PASSWORD},
                {"performance_glitch_user", PASSWORD}
        };
    }

    @Test(dataProvider = "userCredentials")
    public void testSauceDemoSuccessfulLogin(String username, String password) {
        openSauceDemoHomePage();

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        WebElement appLogo = driver.findElement(By.className("app_logo"));

        Assert.assertEquals(appLogo.getText(), "Swag Labs");
    }

    private void openSauceDemoHomePage() {
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testProductsButton () {
        automationexercise();
        WebElement products = driver.findElement(By.xpath("//a[@href='/products']"));

        Assert.assertEquals(products.getText(), " Products");
    }
    @Test
    public void testEmail() {
        automationexercise();
        WebElement cart = driver.findElement(By.xpath("//a[@href='/view_cart']"));
        cart.click();

        WebElement field = driver.findElement(By.id("susbscribe_email"));
        field.sendKeys("Ted@gmail.com");

        WebElement go = driver.findElement(By.cssSelector(".fa.fa-arrow-circle-o-right"));
        go.click();

        WebElement successfulMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-success.alert")));

        Assert.assertEquals(successfulMessage.getText(), "You have been successfully subscribed!");
    }
}
