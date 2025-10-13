package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class SergeyVyshkvarokTest {
    private WebDriver driver;

    @BeforeSuite
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.automationexercise.com/");
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void exit() {
        driver.quit();
    }

    @Test
    public void testPositiveLogin() {
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.name("email")).sendKeys("veshkvarok@mail.ru");
        driver.findElement(By.name("password")).sendKeys("Questiov!#13");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//a[@href='/logout']")).getText(), "Logout");
    }
}