package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class StankewichTest {

    @Test
    public void testPageTitle() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.get("https://frontends-demo.vercel.app/");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Welcome to Shopware Frontends! | Shopware Frontends Demo Store", "Заголовок страницы не совпадает!");
        WebElement searchAuth = driver.findElement(By.cssSelector(".whitespace-nowrap"));
        searchAuth.click();
        WebElement signUpButton = driver.findElement(By.cssSelector("[data-testid='login-sign-up-link']"));
        signUpButton.click();
        WebElement registrationForm = driver.findElement(By.xpath("//form[@data-testid='registration-form']//h3"));
        String headerText = registrationForm.getText();
        Assert.assertEquals(headerText, "I am new here.", "Заголовок формы регистрации не совпадает!");
        driver.quit();
    }
}
