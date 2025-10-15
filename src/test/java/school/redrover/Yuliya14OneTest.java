package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Yuliya14OneTest {

    @Test
    public void testSearchBook(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationbookstore.dev/");

        driver.findElement(By.xpath("//*[@id=\"searchBar\"]")).sendKeys("java for testers");

        WebElement element = driver.findElement(By.xpath("//*[@id=\"pid5_title\"]"));
        Assert.assertEquals(element.getText(), "Java For Testers");

        driver.close();
    }
}

