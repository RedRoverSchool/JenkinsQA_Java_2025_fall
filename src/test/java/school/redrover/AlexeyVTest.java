package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlexeyVTest {
    WebDriver driver = new ChromeDriver();

    @Test
    public void testButton() {
        driver.get("https://seleniumbase.io/demo_page");

        WebElement button = driver.findElement(By.id("myButton"));

        button.click();

        String clcolore = button.getAttribute("style");

        Assert.assertEquals(clcolore, "color: purple;");

        driver.quit();
    }

    @Test
    public void testLoginFielld() {
        driver.get("http://automationexercise.com");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a/i"));

        loginButton.click();

        WebElement loginField = driver.findElement(By.cssSelector("[data-qa=\"login-email\"]"));
        loginField.sendKeys("hellomail.com");

        String validationMessage = loginField.getAttribute("validationMessage");

        WebElement loginButton2 = driver.findElement(By.cssSelector("[data-qa=\"login-button\"]"));
        loginButton2.click();

        Assert.assertTrue(validationMessage.contains("Please include an '@'"),
                "Expected validation message not displayed!");

        driver.quit();

    }

    @Test
    public void testVisible() {

        //1. Launch browser
        //2. Navigate to url 'http://automationexercise.com'
        //3. Verify that home page is visible successfully
        //4. Click on 'Test Cases' button
        //5. Verify user is navigated to test cases page successfully

        driver.get("http://automationexercise.com");

        Assert.assertTrue(driver.getTitle().equals("Automation Exercise"));

        WebElement testCasesButton = driver.findElement(By.xpath("//a[contains(text(),'Test Cases')]"));

        testCasesButton.click();

        Assert.assertTrue(driver.getTitle().equals("Automation Practice Website for UI Testing - Test Cases"));

        driver.quit();
    }
}
