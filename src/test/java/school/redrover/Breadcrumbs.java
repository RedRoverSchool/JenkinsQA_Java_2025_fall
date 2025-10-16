package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Breadcrumbs {

    @Test
    public void mikhailOtest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href='/login']")).click();

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys("John");
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys("test_site123@gmail.com");

        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();

        Thread.sleep(1000);

        WebElement titlesRadioButton = driver.findElement(By.id("id_gender1"));
        titlesRadioButton.click();

        driver.findElement(By.cssSelector("[data-qa='password']")).sendKeys("28inT6yy@qKYDqz");

        WebElement dropdownDayOfBirth = driver.findElement(By.cssSelector("[data-qa='days'] option:nth-child(5)"));
        dropdownDayOfBirth.click();
        WebElement dropdownMonthOfBirth = driver.findElement(By.cssSelector("[data-qa='months'] option:nth-child(3)"));
        dropdownMonthOfBirth.click();
        WebElement dropdownYearOfBirth = driver.findElement(By.cssSelector("[data-qa='years'] option:nth-child(31)"));
        dropdownYearOfBirth.click();

        WebElement checkboxNewsletter = driver.findElement(By.id("newsletter"));
        checkboxNewsletter.click();
        WebElement checkboxOffers = driver.findElement(By.id("optin"));
        checkboxOffers.click();

        driver.findElement(By.cssSelector("[data-qa='first_name']")).sendKeys("John");
        driver.findElement(By.cssSelector("[data-qa='last_name']")).sendKeys("Smith");
        driver.findElement(By.cssSelector("[data-qa='address']")).sendKeys("Acme Innovations Inc., 123 Main Street Apt 4B, Anytown, NY 12345");
        driver.findElement(By.cssSelector("[data-qa='address2']")).sendKeys("Smallville, CA 90210");
        WebElement dropdownCountry = driver.findElement(By.cssSelector("#country > option:nth-child(2)"));
        dropdownCountry.click();
        driver.findElement(By.cssSelector("[data-qa='state']")).sendKeys("NY");
        driver.findElement(By.cssSelector("[data-qa='city']")).sendKeys("New York");
        driver.findElement(By.cssSelector("[data-qa='zipcode']")).sendKeys("12345");
        driver.findElement(By.cssSelector("[data-qa='mobile_number']")).sendKeys("+1 (123) 456-7890");

        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        Thread.sleep(1000);

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href='/delete_account']")).click();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        driver.quit();
    }
}
