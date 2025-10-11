package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NatPTest {
    //Test Case 1: Register User

    @Test
    public void registerUser() throws InterruptedException {
//        1. Launch browser
        WebDriver driver = new ChromeDriver();

//        2. Navigate to url 'http://automationexercise.com'
        driver.get("http://automationexercise.com");

//        3. Verify that home page is visible successfully
        WebElement consent = driver.findElement(By.xpath("//button[@aria-label='Consent']"));
        consent.click();
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

//        4. Click on 'Signup / Login' button
        driver.findElement(By.xpath("//a[@href='/login']")).click();

//        5. Verify 'New User Signup!' is visible
        WebElement signup = driver.findElement(By.cssSelector("#form div:nth-child(3) h2"));
        Assert.assertEquals(signup.getText(),"New User Signup!");

//        6. Enter name and email address
        String username = "to be re-used in step16";
        driver.findElement(By.name("name")).sendKeys(username);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("some.email@email.com");

//        7. Click 'Signup' button
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).submit();

//        8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
        Assert.assertEquals(driver.findElement(By.cssSelector("#form b")).getText(),"ENTER ACCOUNT INFORMATION");

//        9. Fill details: Title, Name (pre-entered), Email (pre-entered), Password, Date of birth (dd-mm-yy)
        driver.findElement(By.id("id_gender2")).click();
        driver.findElement(By.id("password")).sendKeys("135!#%qetQET");
        driver.findElement(By.cssSelector("#days > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#months > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#years > option:nth-child(17")).click();

//        10. Select checkbox 'Sign up for our newsletter!'
        driver.findElement(By.id("newsletter")).click();

//        11. Select checkbox 'Receive special offers from our partners!'
        driver.findElement(By.id("optin")).click();

//        12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.id("first_name")).sendKeys("First name");
        driver.findElement(By.id("last_name")).sendKeys("Last name");
        driver.findElement(By.id("company")).sendKeys("Company");
        driver.findElement(By.id("address1")).sendKeys("Address");
        driver.findElement(By.id("address2")).sendKeys("Address 2");
        driver.findElement(By.cssSelector("#country > option:nth-child(4)")).click();
        driver.findElement(By.id("state")).sendKeys("State");
        driver.findElement(By.id("city")).sendKeys("City");
        driver.findElement(By.id("zipcode")).sendKeys("1233");
        driver.findElement(By.id("mobile_number")).sendKeys("123456");

//        13. Click 'Create Account button'
        driver.findElement(By.cssSelector("#form button")).submit();

//        14. Verify that 'ACCOUNT CREATED!' is visible
        Assert.assertEquals(driver.findElement(By.cssSelector("#form b")).getText(),"ACCOUNT CREATED!");

//        15. Click 'Continue' button
        driver.findElement(By.cssSelector("#form a")).click();

//        16. Verify that 'Logged in as username' is visible
        WebElement loggedIn = driver.findElement(By.cssSelector("#header li:nth-child(10) > a"));
        Assert.assertEquals(loggedIn.getText(),"Logged in as " + username);

//        17. Click 'Delete Account' button
        driver.findElement(By.xpath("//a[@href='/delete_account']")).click();

        Thread.sleep(500);

//        18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        Assert.assertEquals(driver.findElement(By.cssSelector("#form b")).getText(),"ACCOUNT DELETED!");
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        driver.quit();
    }
}
