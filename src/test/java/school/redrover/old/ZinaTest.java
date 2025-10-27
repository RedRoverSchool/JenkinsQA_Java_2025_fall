package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class ZinaTest {
    @Test
    public void loginValid() {
        WebDriver driver = new ChromeDriver();
        try {


            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            String loginName = "Admin";
            String pass = "admin123";

            driver.findElement(By.name("username")).sendKeys(loginName, Keys.TAB, pass, Keys.ENTER);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='oxd-topbar-header-breadcrumb']")));
            Assert.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        } finally {
            driver.quit();
        }

    }

    @Test
    public void loginNoPass() {
        WebDriver driver = new ChromeDriver();
        try {


            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            String loginName = "Admin";
            String pass = "";

            driver.findElement(By.name("username")).sendKeys(loginName, Keys.TAB, pass, Keys.ENTER);
            WebElement errorMessage = driver.findElement(By.xpath("//span[text()='Required']"));

            Assert.assertTrue(errorMessage.isDisplayed());
        } finally {
            driver.quit();
        }
    }
}

