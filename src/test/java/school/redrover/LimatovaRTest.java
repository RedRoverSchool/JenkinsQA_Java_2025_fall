package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.By.name;

public class LimatovaRTest {

    WebDriver driver;
    private static final String LOGIN = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ofMillis(3000));
    }

    @Test
    public void testLogin() {

        driver.get("https://www.saucedemo.com/");

        driver.findElement(name("user-name")).sendKeys(LOGIN);
        driver.findElement(name("password")).sendKeys(PASSWORD);
        driver.findElement(name("login-button")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }

    @Test
    public void testNegative() {

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@id='user-name']"))
                .sendKeys("123");
        driver.findElement(By.xpath("//input[@id='password']"))
                .sendKeys("123");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        WebElement errorNotification = driver.findElement(By.xpath("//h3[text()='Epic sadface: " +
                "Username and password do not match any user in this service']"));

        Assert.assertNotNull(errorNotification);
    }

    @AfterTest
    private void close() {
        driver.quit();
    }
}
