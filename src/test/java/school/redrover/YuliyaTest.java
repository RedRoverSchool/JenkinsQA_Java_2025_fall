package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class YuliyaTest {
            @Test
        public void testEnter() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.get("https://dev.trexer.ru/auth/login");
            System.out.println(driver.getTitle());

            Thread.sleep(500);
            WebElement emailLabel = driver.findElement(By.cssSelector("input"));
            emailLabel.sendKeys("prodigy@mail.ru");

            WebElement submitButton = driver.findElement(By.cssSelector("button"));

            Assert.assertTrue(submitButton.isDisplayed(), "Элемент не активен");
            driver.quit();
        }
}
