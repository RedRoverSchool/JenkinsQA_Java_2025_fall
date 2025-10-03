package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SvitlanaNTest {
    @Test
    public void testSauseDemoVisualUser() {

        WebDriver drive = new ChromeDriver();
        drive.get("https://www.saucedemo.com/");

        Assert.assertEquals(drive.getTitle(), "Swag Labs");

        drive.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement userName = drive.findElement(By.id("user-name"));
        userName.sendKeys("visual_user");

        WebElement passw = drive.findElement(By.id("password"));
        passw.sendKeys("secret_sauce");

        WebElement submitButton = drive.findElement(By.name("login-button"));
        submitButton.click();

        WebElement page = drive.findElement(By.className("title"));

        Assert.assertEquals(page.getText(), "Products");

        drive.quit();
    }

    @Test
    public void testSauceDemoStandardUser() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.getTitle();

        WebElement usName = driver.findElement(By.id("user-name"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));

        usName.sendKeys("standard_user");
        pass.sendKeys("secret_sauce");
        login.click();

        WebElement page = driver.findElement(By.className("title"));

        Assert.assertEquals(page.getText(), "Products");

        driver.quit();
    }

    @Test
    public void testNegativeLogin() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.getTitle();

        WebElement usName = driver.findElement(By.id("user-name"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));

        usName.sendKeys("error_user");
        pass.sendKeys("passworddd");
        login.click();

        WebElement messageError =
                driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));

        Assert.assertEquals(messageError.getText(),
                "Epic sadface: Username and password do not match any user in this service");


    }
}
