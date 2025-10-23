package school.redrover.old;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;

@Ignore
public class DinaraTest {
    private final String baseUrl = "https://seleniumbase.io/demo_page";
    private static WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }
    @AfterMethod
    public void tearDown(){
        if (driver!= null) driver.quit();
    }

    @Test
    public void placeholderTextFieldTest() {
        //arrange
        driver.get(baseUrl);
        WebElement placeholderField = driver.findElement(By.id("placeholderText"));
        //act
        var text = placeholderField.getAttribute("placeholder");
        //assert
        Assert.assertEquals(text, "Placeholder Text Field");
    }

    @Test
    public void radioButtonSelectedTest(){
        //arrange
        driver.get(baseUrl);
        WebElement radioButton = driver.findElement(By.id("radioButton2"));
        //assert 1
        Assert.assertFalse(radioButton.isSelected());
        //act
        radioButton.click();
        //assert 2
        Assert.assertTrue(radioButton.isEnabled());
    }
    @Test
    public void progressBarStatusTest(){
        //arrange
        driver.get(baseUrl);
        //act
        WebElement progressBarStatus = driver.findElement(By.id("progressBar"));
        WebElement progressBarLabel = driver.findElement(By.id("progressLabel"));
        //assert
        Assert.assertEquals(progressBarLabel.getText(), "Progress Bar: (50%)");
        Assert.assertEquals(progressBarStatus.getAttribute("value"), "50");
    }

    @Test
    public void dropdownMenuOnHoverTest(){
        //arrange
        driver.get(baseUrl);
        WebElement hoverDropdown = driver.findElement(By.id("myDropdown"));
        //act
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverDropdown).perform();
        WebElement dropdownMenu = driver.findElement(By.className("dropdown-content"));
        //assert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(dropdownMenu.getText().contains("Link One"));
        softAssert.assertTrue(dropdownMenu.getText().contains("Link Two"));
        softAssert.assertTrue(dropdownMenu.getText().contains("Link Three"));
        softAssert.assertAll();
    }
    @Test
    public void testCasesPageTest() {
        //1. Launch browser
        //2. Navigate to url 'http://automationexercise.com'
        driver.get("https://automationexercise.com");
        String homePageTitle = driver.getTitle();
        //3. Verify that home page is visible successfully
        Assert.assertEquals(homePageTitle, "Automation Exercise");
        //4. Click on 'Test Cases' button
        WebElement testCasesButton = driver.findElement(By.xpath("//a[contains(text(),'Test Cases')]"));
        testCasesButton.click();
        //5. Verify user is navigated to test cases page successfully
        Assert.assertEquals(driver.getTitle(), "Automation Practice Website for UI Testing - Test Cases");
    }
    @Test
    public void connectUsPageTest() {
        //1. Launch browser
        //2. Navigate to url 'http://automationexercise.com'
        driver.get("https://automationexercise.com");
        //3. Verify that home page is visible successfully
        Assert.assertEquals( driver.getTitle(), "Automation Exercise");

        // 4. Click on 'Contact Us' button
        WebElement contactUsButton = driver.findElement(By.xpath("//a[contains(text(),'Contact us')]"));
        contactUsButton.click();
        //5. Verify 'GET IN TOUCH' is visible
        WebElement getInTouchText = driver.findElement(By.xpath("//h2[@class='title text-center' and text()='Get In Touch']"));
        Assert.assertEquals(getInTouchText.getText(), "GET IN TOUCH");
        Assert.assertTrue(getInTouchText.isDisplayed());

        //6. Enter name, email, subject and message
        driver.findElement(By.xpath("//input[@data-qa='name']")).sendKeys("Name Test");
        driver.findElement(By.xpath("//input[@data-qa='email']"))
                .sendKeys("testemail@gmail.com");
        driver.findElement(By.xpath("//input[@data-qa='subject']")).sendKeys("Return");
        driver.findElement(By.xpath("//textarea[@data-qa='message']"))
                .sendKeys("Some Super Awesome Message!!");

        //7. Upload file
        WebElement uploadButton = driver.findElement(By.xpath("//input[@name='upload_file']"));
        File uploadFile = new File("/Volumes/data/RedRover_2025/Project1/src/main/resources/report.csv");
        String absolutePath = uploadFile.getAbsolutePath();
        uploadButton.sendKeys(absolutePath);

        //8. Click 'Submit' button
        driver.findElement(By.xpath("//input[@data-qa='submit-button']")).click();
        //9. Click OK button
        Alert alert = driver.switchTo().alert();
        alert.accept();

        //10. Verify success message 'Success! Your details have been submitted successfully.' is visible
        WebElement alertSuccess = driver.findElement(By.xpath("//div[contains(@class, 'alert-success')]"));
        Assert.assertEquals(alertSuccess.getText(),"Success! Your details have been submitted successfully.");

        //11. Click 'Home' button and verify that landed to home page successfully
        driver.findElement(By.xpath("//a[@class='btn btn-success']")).click();
        Assert.assertEquals( driver.getTitle(), "Automation Exercise");
    }

    @Test
    public void testPriceOfBooking() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://automationintesting.online/");

        Thread.sleep(3000);
        final int numDays = 3;
        final int expectedTotal = 100 * (numDays + 1) + 25 + 15;

        //JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);
        WebElement calendarCheckOut = driver.findElement(By.xpath("//label[@for='checkout']/following::input[1]"));
        //js.executeScript("arguments[0].scrollIntoView(true);", calendarCheckOut);
        actions.moveToElement(calendarCheckOut).click().perform();

        driver.findElement(By.xpath("//div[contains(@class,'day--selected')]/following-sibling::*[%d]".formatted(numDays))).click();
        Thread.sleep(2000);

        WebElement checkAvailability = driver.findElement(By.xpath("//button[text()='Check Availability']"));
        actions.moveToElement(checkAvailability).click().perform();

        Thread.sleep(2000);
        driver.findElement(By.xpath("(//a[text()='Book now'])[1]")).click();
        Thread.sleep(2000);
        String totalText = driver.findElement(By.xpath("//span[text()='Total']/following-sibling::span")).getText();
        //assert
        Assert.assertEquals(expectedTotal, Integer.parseInt(totalText.substring(1)));
    }


}
