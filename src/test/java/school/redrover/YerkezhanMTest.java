package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class YerkezhanMTest {

    @Test
    public void testCheckTitleIsCorrect() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.lambdatest.com/selenium-playground/");

        WebElement title = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/section[1]/div/h1"));

        Assert.assertEquals(title.getText(), "Selenium Playground");

        driver.quit();

    }
}
