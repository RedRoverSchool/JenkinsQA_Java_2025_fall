package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class VladimirSitTest {

    private final static String host = "https://demoqa.com/text-box";

    private final static By fullNameInput = By.id("userName");
    private final static By emailInput = By.id("userEmail");
    private final static By currentAddressInput = By.id("currentAddress");
    private final static By permanentAddressInput = By.id("permanentAddress");
    private final static By submitButtonInput = By.id("submit");

    private final static String fullName = "Ivanov Ivan";
    private final static String email= "123@gmail.com";
    private final static String currentAddress = "Moscow";
    private final static String permanentAddress = "Samara";

    @Test
    private static void testDemoQA() {

        WebDriver driver = new ChromeDriver();

        driver.get(host);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(fullNameInput).sendKeys(fullName);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(currentAddressInput).sendKeys(currentAddress);
        driver.findElement(permanentAddressInput).sendKeys(permanentAddress);

        driver.findElement(submitButtonInput).click();

        Assert.assertEquals(driver.findElement(By.id("name")).getText(), "Name:" + fullName);
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), "Email:" + email);
        Assert.assertEquals(driver.findElement(By.cssSelector("p#currentAddress")).getText(), "Current Address :" + currentAddress);
        Assert.assertEquals(driver.findElement(By.cssSelector("p#permanentAddress")).getText(), "Permananet Address :" + permanentAddress);

        driver.quit();
    }

}
