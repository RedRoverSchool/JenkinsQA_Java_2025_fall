package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AkikoTest {

    public static final String LOGIN = "admin@admin.com";
    public static final String PASSWORD = "admin123";

    @Test
    public void testPositiveLogin() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/auth_ecommerce");

        driver.findElement(By.name("emailAddress")).sendKeys(LOGIN);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("submitLoginBtn")).click();

        Assert.assertTrue(driver.findElement(By.id("logout")).isDisplayed(),
                "Logout button should be displayed after successful login");

        driver.close();
    }
}
