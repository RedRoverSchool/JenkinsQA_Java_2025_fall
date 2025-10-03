package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;



public class DZubovichTest {
    @Test
    public void testRegisterUser(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        SoftAssert softAssert = new SoftAssert();

        // Verify that home page is visible successfully
        WebElement logo = driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-4 > div > a > img"));
        softAssert.assertTrue(logo.isDisplayed(), "Page is not visible");

        // Click on 'Signup / Login' button
        driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a")).click();

        // Verify 'New User Signup!' is visible
        WebElement textNewUser = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > h2"));
        softAssert.assertTrue(textNewUser.isDisplayed(),"New User Signup is not visible");

        // Enter name and email address
        WebElement nameField = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=text]:nth-child(2)"));
        nameField.sendKeys("Dariya");

        WebElement emailField = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=email]:nth-child(3)"));
        emailField.sendKeys("testdariya@gmail.com");

        //Click 'Signup' button
        driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > button")).click();

        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        softAssert.assertTrue(driver.findElement(By.cssSelector("#form > div > div > div > div > h2 > b")).isDisplayed(), "ENTER ACCOUNT INFORMATION is not visible");

        // Fill details: Title, Name, Email, Password, Date of birth
        // Check if the radio button "Mrs." selected or not
        if(!driver.findElement(By.cssSelector("#id_gender2")).isSelected()){
            driver.findElement(By.cssSelector("#id_gender2")).click();
        }

        // Check is the fields is filled with correct data
        String valueNameField = driver.findElement(By.cssSelector("#name")).getAttribute("value");
        softAssert.assertEquals(valueNameField, "Dariya");

        String valueEmailField = driver.findElement(By.cssSelector("#email")).getAttribute("value");
        softAssert.assertEquals(valueEmailField, "testdariya@gmail.com");

        // Fill password
        driver.findElement(By.cssSelector("#password")).sendKeys("123Password");

        // Check day, month, year in dropdown
        Select daySelect = new Select(driver.findElement(By.cssSelector("#days")));
        daySelect.selectByValue("15");

        Select monthSelector = new Select(driver.findElement(By.cssSelector("#months")));
        monthSelector.selectByValue("9");

        Select yearSelect = new Select(driver.findElement(By.cssSelector("#years")));
        yearSelect.selectByValue("2000");

        //Scroll 300px
        Actions actions = new Actions(driver);
        actions.scrollByAmount(0, 300).perform();

        // Select checkbox 'Sign up for our newsletter!'
        driver.findElement(By.cssSelector("#newsletter")).click();

        // Select checkbox 'Receive special offers from our partners!'
        driver.findElement(By.cssSelector("#optin")).click();

        //Scroll 1000px
        actions.scrollByAmount(0, 1000).perform();

        //Fill details: First name, Last name, Company, Address, Address2,
        // Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.cssSelector("#first_name")).sendKeys("Dariya");
        driver.findElement(By.cssSelector("#last_name")).sendKeys("Zubovich");
        driver.findElement(By.cssSelector("#company")).sendKeys("Company X");
        driver.findElement(By.cssSelector("#address1")).sendKeys("123 Maple Street, P.O. Box 789, Company X");

        Select countrySelect = new Select(driver.findElement(By.cssSelector("#country")));
        countrySelect.selectByValue("Canada");

        driver.findElement(By.cssSelector("#state")).sendKeys("Alberta");
        driver.findElement(By.cssSelector("#city")).sendKeys("Brooks");
        driver.findElement(By.cssSelector("#zipcode")).sendKeys("12345-6789");
        driver.findElement(By.cssSelector("#mobile_number")).sendKeys("+1 (437) 555-0001");

        //Scroll 500px
        actions.scrollByAmount(0, 500).perform();

        //  Click 'Create Account button'
        driver.findElement(By.cssSelector("#form > div > div > div > div > form > button")).click();

        // Verify that 'ACCOUNT CREATED!' is visible
        softAssert.assertTrue(driver.findElement(By.cssSelector("#form > div > div > div > h2 > b")).isDisplayed(),"Message is not visible");

        // Click 'Continue' button
        driver.findElement(By.cssSelector("#form > div > div > div > div > a")).click();

        // Verify that 'Logged in as username' is visible
        softAssert.assertTrue(driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10) > a")).isDisplayed(),"Logged in as Dariya is not displayed");

        // Click 'Delete Account' button
        driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(5) > a")).click();

        // Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        softAssert.assertTrue(driver.findElement(By.cssSelector("#form > div > div > div > h2 > b")).isDisplayed(),"Message ACCOUNT DELETED is not displayed");

        driver.quit();

    }

}
