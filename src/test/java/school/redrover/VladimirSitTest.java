package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class VladimirSitTest {

    static String host = "https://demoqa.com/text-box";

    static By fullNameInput = By.id("userName");
    static By emailInput = By.id("userEmail");
    static By currentAddressInput = By.id("currentAddress");
    static By permanentAddressInput = By.id("permanentAddress");
    static By submitButtonInput = By.id("submit");

    static String fullName = "Ivanov Ivan";
    static String email= "123@gmail.com";
    static String currentAddress = "Moscow";
    static String permanentAddress = "Samara";

    @Test
    public static void testDemoQA() {

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
