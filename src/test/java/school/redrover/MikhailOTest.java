package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class MikhailOTest {

    @Test
    public void testRegisterUser() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");

        // Verify that home page is visible successfully

        String expectedTitle = "automation exercise";
        String actualTitle = driver.getTitle().toLowerCase();
        Assert.assertEquals(actualTitle, expectedTitle);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // Click on 'Signup / Login' button

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/login\"]")).click();

        // Verify 'New User Signup!' is visible

        WebElement newUserText = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > h2"));
        Assert.assertEquals(newUserText.getText().toLowerCase(), "new user signup!");

        // Enter name and email address

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys("John");

        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys("test_site123@gmail.com");

        // Click 'Signup' button

        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();

        Thread.sleep(1000);

        // Verify that 'ENTER ACCOUNT INFORMATION' is visible

        WebElement information = driver.findElement(By.cssSelector("#form > div > div > div > div > h2 > b"));
        Assert.assertEquals(information.getText().toLowerCase(), "enter account information");

        // Fill details: Title, Password, Date of birth

        driver.findElement(By.id("id_gender1")).click();

        driver.findElement(By.cssSelector("[data-qa='password']")).sendKeys("28inT6yy@qKYDqz");

        driver.findElement(By.cssSelector("[data-qa='days']"));
        driver.findElement(By.cssSelector("#days > option:nth-child(5)")).click();

        driver.findElement(By.cssSelector("[data-qa='months']"));
        driver.findElement(By.cssSelector("#months > option:nth-child(3)")).click();

        driver.findElement(By.cssSelector("[data-qa='years']"));
        driver.findElement(By.cssSelector("#years > option:nth-child(31)")).click();

        // Select checkbox 'Sign up for our newsletter!'

        driver.findElement(By.id("newsletter")).click();

        // Select checkbox 'Receive special offers from our partners!'

        driver.findElement(By.id("optin")).click();

        // Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number

        driver.findElement(By.cssSelector("[data-qa='first_name']")).sendKeys("John");

        driver.findElement(By.cssSelector("[data-qa='last_name']")).sendKeys("Smith");

        driver.findElement(By.cssSelector("[data-qa='address']")).sendKeys("Acme Innovations Inc., 123 Main Street Apt 4B, Anytown, NY 12345");

        driver.findElement(By.cssSelector("[data-qa='address2']")).sendKeys("Smallville, CA 90210");

        driver.findElement(By.cssSelector("[data-qa='country']"));
        driver.findElement(By.cssSelector("#country > option:nth-child(2)")).click();


        driver.findElement(By.cssSelector("[data-qa='state']")).sendKeys("NY");

        driver.findElement(By.cssSelector("[data-qa='city']")).sendKeys("New York");

        driver.findElement(By.cssSelector("[data-qa='zipcode']")).sendKeys("12345");

        driver.findElement(By.cssSelector("[data-qa='mobile_number']")).sendKeys("+1 (123) 456-7890");

        // Click 'Create Account button'

        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();

        // Verify that 'ACCOUNT CREATED!' is visible

        WebElement accountCreated = driver.findElement(By.cssSelector("[data-qa='account-created']"));
        Assert.assertEquals(accountCreated.getText().toLowerCase(), "account created!");
        
        // Click 'Continue' button

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        Thread.sleep(1000);

        // Verify that 'Logged in as username' is visible

        WebElement loggedInAs = driver.findElement(By.cssSelector("#header .shop-menu li > a > b"));
        Assert.assertEquals(loggedInAs.getText(), "John");

        // Click 'Delete Account' button

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/delete_account\"]")).click();

        // Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button

        WebElement accountDeleted = driver.findElement(By.cssSelector("[data-qa='account-deleted']"));
        Assert.assertEquals(accountDeleted.getText().toLowerCase(), "account deleted!");

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
    }
}
