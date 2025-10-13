package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlbinaMTest {

    @Test
    public void testSauceDemo() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");

        Assert.assertEquals(driver.getTitle(), "Swag Labs");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]")).getText(), "Swag Labs");

        WebElement usernameBox = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        usernameBox.sendKeys("standard_user");
        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("secret_sauce");
        WebElement button = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        button.click();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText(), "Sauce Labs Backpack");

        driver.quit();
    }
}
