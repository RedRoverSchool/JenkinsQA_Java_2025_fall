package school.redrover;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlevtinaSTest {

    @Test
    public void testPriceOfBooking()  {
        final int numDays = 3;
        final int expectedTotal = 100 * (numDays + 1) + 25 + 15;

        WebDriver driver = new ChromeDriver();
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
