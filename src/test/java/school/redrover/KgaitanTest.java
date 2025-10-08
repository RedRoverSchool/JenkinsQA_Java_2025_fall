package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KgaitanTest {
    @Test
    public void testSelenium() {

        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");

        String fullName = "Harry Potter";
        String email = "harrypotter@gmail.com";
        String currentAddress = "The Cupboard under the Stairs, 4 Privet Drive, Little Whinging, SURREY";
        String permanentAddress = "Hogwarts School of Witchcraft and Wizardry, The Scottish Highlands, United Kingdom";

        WebElement fullNameBox = driver.findElement(By.xpath("//*[@id='userName']"));
        fullNameBox.sendKeys(fullName);

        WebElement emailBox = driver.findElement(By.xpath("//*[@id='userEmail']"));
        emailBox.sendKeys(email);

        WebElement currentAddressBox = driver.findElement(By.xpath("//*[@id='currentAddress']"));
        currentAddressBox.sendKeys(currentAddress);

        WebElement permanentAddressBox = driver.findElement(By.xpath("//*[@id='permanentAddress']"));
        permanentAddressBox.sendKeys(permanentAddress);

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        String actualName = driver.findElement(By.xpath("//p[@id='name']")).getText();
        String actualEmail = driver.findElement(By.xpath("//p[@id='email']")).getText();
        String actualCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']")).getText();
        String actualPermanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText();


        Assert.assertEquals(actualName, "Name:" + fullName);
        Assert.assertEquals(actualEmail,"Email:" + email);
        Assert.assertEquals(actualCurrentAddress, "Current Address :" + currentAddress);
        Assert.assertEquals(actualPermanentAddress,"Permananet Address :" + permanentAddress);

       driver.quit();
    }
}
