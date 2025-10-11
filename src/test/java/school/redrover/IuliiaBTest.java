package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class IuliiaBTest {

    @Test
    public void testSeleniumWebForm() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textInput = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        textInput.sendKeys("test@test.com");

        WebElement password = driver.findElement(By.xpath("//input[@name='my-password']"));
        password.sendKeys("Pass_123");

        WebElement textarea = driver.findElement(By.xpath("//textarea[@name='my-textarea']"));
        textarea.sendKeys("Selenium web form test");

        WebElement checkedCheckbox = driver.findElement(By.xpath("//input[@id='my-check-1']"));
        if (checkedCheckbox.isSelected()) {
            checkedCheckbox.click();
        }

        WebElement defaultCheckbox = driver.findElement(By.xpath("//input[@id='my-check-2']"));
        if (!defaultCheckbox.isSelected()) {
            defaultCheckbox.click();
        }

        WebElement defaultRadio = driver.findElement(By.xpath("//input[@id='my-radio-2']"));
        defaultRadio.click();

        WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
        submit.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement message = driver.findElement(By.xpath("//p[@id='message']"));
        String text = message.getText();

        Assert.assertEquals(text, "Received!");
        driver.quit();
    }
}