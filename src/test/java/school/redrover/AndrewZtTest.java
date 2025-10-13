package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class AndrewZtTest {

    @Test
    public void testLogin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        Thread.sleep(1000);

        WebElement inputText = driver.findElement(By.name("username"));
        inputText.sendKeys("Admin");

        WebElement inputPassword = driver.findElement(By.name("password"));
        inputPassword.sendKeys("admin123");

        WebElement submitButton = driver.findElement(By.xpath("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button"));
        submitButton.click();

        Thread.sleep(1500);

        WebElement pageText = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/header/div[1]/div[1]/span/h6"));

        String actualResultString = pageText.getText();
        String expectedResultString = "Dashboard";

        Assert.assertEquals(actualResultString, expectedResultString);

        driver.quit();
    }

    private WebDriver driver;
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
