package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class AleksandrATest {
    @Test
    public void getTitleFromSecondPage() {



        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com");

        WebElement login = driver.findElement(By.id("user-name"));
        login.sendKeys("standard_user");

        driver.findElement(By.id("password")).sendKeys("secret_sauce");



        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[1]/div[2]/div")).getText(), "Swag Labs");

        driver.quit();
    }
}
