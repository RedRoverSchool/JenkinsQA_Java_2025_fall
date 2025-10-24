package school.redrover.old.task15;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class EkimenkoKateTest {
    @Test
    public void testRegisterUser() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");

        // 1. Verify that home page is visible successfully

        WebElement homeButton = driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/\"]"));
        String color = homeButton.getAttribute("style");
        Assert.assertNotNull(color);
        Assert.assertTrue(color.contains("color: orange;"), "Button isn`t orange");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // 2. Click on 'Signup / Login' button+

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/login\"]")).click();

        // 3. Verify 'New User Signup!' is visible

        WebElement newUserText = driver.findElement(By.cssSelector(".signup-form h2"));
        Assert.assertTrue(newUserText.isDisplayed(), "Title 'New User Signup!' isn`t visible on page");
        Assert.assertEquals(newUserText.getText().toLowerCase(), "new user signup!", "'New User Signup!' isn`t visible");

        // 4. Enter name and email address

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys("John");

        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys("test_site123@gmail.com");

        // 5. Click 'Signup' button

        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();

        Thread.sleep(1000);

        // 6. Verify that 'ENTER ACCOUNT INFORMATION' is visible

        Assert.assertTrue(driver.findElement(By.cssSelector("div.login-form > h2 > b")).isDisplayed(),
                "Title 'ENTER ACCOUNT INFORMATION' isn`t visible");

        // 7. Fill details: Title, Password, Date of birth

        driver.findElement(By.id("id_gender1")).click();

        driver.findElement(By.cssSelector("[data-qa='password']")).sendKeys("28inT6yy@qKYDqz");

        driver.findElement(By.cssSelector("[data-qa='days'] option:nth-child(5)")).click();

        driver.findElement(By.cssSelector("[data-qa='months'] option:nth-child(3)")).click();

        driver.findElement(By.cssSelector("[data-qa='years'] option:nth-child(31)")).click();

        // 8. Select checkbox 'Sign up for our newsletter!'

        driver.findElement(By.id("newsletter")).click();

        // 9. Select checkbox 'Receive special offers from our partners!'

        driver.findElement(By.id("optin")).click();

        // 10. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number

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

        // 11. Click 'Create Account button'

        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();

        // 12. Verify that 'ACCOUNT CREATED!' is visible

        WebElement accountCreated = driver.findElement(By.cssSelector("[data-qa='account-created']"));
        Assert.assertEquals(accountCreated.getText().toLowerCase(), "account created!");

        // 13. Click 'Continue' button

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        Thread.sleep(1000);

        // 14. Verify that 'Logged in as username' is visible

        WebElement loggedInAs = driver.findElement(By.cssSelector("#header .shop-menu li > a > b"));
        Assert.assertEquals(loggedInAs.getText(), "John");

        // 15. Click 'Delete Account' button

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/delete_account\"]")).click();

        // 16. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button

        WebElement accountDeleted = driver.findElement(By.cssSelector("[data-qa='account-deleted']"));
        Assert.assertEquals(accountDeleted.getText().toLowerCase(), "account deleted!");

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        driver.quit();
    }
}
