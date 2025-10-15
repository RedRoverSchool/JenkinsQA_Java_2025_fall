package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class BitByBitGroupTest {

    private WebDriver driver = new ChromeDriver();

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

    @Test
    public void testPriceOfBooking()  {
        final int numDays = 3;
        final int expectedTotal = 100 * (numDays + 1) + 25 + 15;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://automationintesting.online/");

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//label[@for='checkout']/following::input[1]"))).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[contains(@class, 'day--selected')]/following-sibling::div[%d]".formatted(numDays)))).click();

        driver.findElement(By.xpath("//button[text()='Check Availability']")).click();

        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Book now'])[1]")));

        Actions actions = new Actions(driver);
        actions.moveToElement(bookButton).click().perform();

        String totalText = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[text()='Total']/following-sibling::span"))).getText();

        Assert.assertEquals(expectedTotal, Integer.parseInt(totalText.substring(1)));
        driver.quit();
    }
}
