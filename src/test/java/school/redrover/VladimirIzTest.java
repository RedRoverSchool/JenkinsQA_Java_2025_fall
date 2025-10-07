package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VladimirIzTest {

    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    @Test
    public void testRegistrationUser() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(USERNAME);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(PASSWORD);

        driver.findElement(By.id("login-button")).click();

        WebElement actualResult = driver.findElement(By.xpath("//div[@class='app_logo']"));
        String value = actualResult.getText();

        Assert.assertEquals(value, "Swag Labs");
        driver.quit();
    }

}
