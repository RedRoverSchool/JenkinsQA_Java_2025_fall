package school.redrover.old;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class AlenaIadTest {
    @Test
    public void testWiseCurrencyConverter() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://wise.com/gb/currency-converter/");
        Thread.sleep(1000);

        double testingValue = 100;

        WebElement sourceInputField = driver.findElement(By.id("source-input"));
        sourceInputField.sendKeys(Keys.CONTROL + "a");
        sourceInputField.sendKeys(Keys.DELETE);

        sourceInputField.sendKeys(String.valueOf(testingValue));

        WebElement targetInputField = driver.findElement(By.id("target-input"));
        String targetInputFieldText = targetInputField.getAttribute("value");

        WebElement rate = driver
                .findElement(By.xpath("//*[@id=\"calculator\"]/div/div/div[2]/div/div/div/div[1]/div/div/div[2]/span[2]"));
        String rateText = rate.getText().split(" ")[3];

        Assert.assertEquals(Double.parseDouble(targetInputFieldText), Double.parseDouble(rateText) * testingValue);

        driver.quit();
    }
}
