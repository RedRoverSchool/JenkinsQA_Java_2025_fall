package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;

public class BitByBitGroupTest {

    private WebDriver driver;

    @BeforeMethod
    public void startBeforeTest() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(){
        if (driver!= null) driver.quit();
    }

    @Test
    public void testButton() {
        driver.get("https://seleniumbase.io/demo_page");

        WebElement button = driver.findElement(By.id("myButton"));

        button.click();

        String clcolore = button.getAttribute("style");

        Assert.assertEquals(clcolore, "color: purple;");

        driver.quit();
    }

    @Test
    public void testLoginFielld() {
        driver.get("http://automationexercise.com");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a/i"));

        loginButton.click();

        WebElement loginField = driver.findElement(By.cssSelector("[data-qa=\"login-email\"]"));
        loginField.sendKeys("hellomail.com");

        String validationMessage = loginField.getAttribute("validationMessage");

        WebElement loginButton2 = driver.findElement(By.cssSelector("[data-qa=\"login-button\"]"));
        loginButton2.click();

        Assert.assertTrue(validationMessage.contains("Please include an '@'"),
                "Expected validation message not displayed!");

        driver.quit();

    }

    @Test
    public void testVisible() {

        //1. Launch browser
        //2. Navigate to url 'http://automationexercise.com'
        //3. Verify that home page is visible successfully
        //4. Click on 'Test Cases' button
        //5. Verify user is navigated to test cases page successfully

        driver.get("http://automationexercise.com");

        Assert.assertTrue(driver.getTitle().equals("Automation Exercise"));

        WebElement testCasesButton = driver.findElement(By.xpath("//a[contains(text(),'Test Cases')]"));

        testCasesButton.click();

        Assert.assertTrue(driver.getTitle().equals("Automation Practice Website for UI Testing - Test Cases"));

        driver.quit();
    }

    @Test
    public void testCategoriesDropdownMenuLinks() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-test='nav-categories']")));

        driver.findElement(By.xpath("//a[@data-test='nav-categories']")).click();

        WebElement handTools = driver.findElement(By.xpath("//a[@data-test='nav-hand-tools']"));
        WebElement powerTools = driver.findElement(By.xpath("//a[@data-test='nav-power-tools']"));
        WebElement otherTools = driver.findElement(By.xpath("//a[@data-test='nav-other']"));
        WebElement specialTools = driver.findElement(By.xpath("//a[contains(text(),'Special Tools')]"));
        WebElement retails = driver.findElement(By.xpath("//a[contains(text(),'Rentals')]"));

        Assert.assertEquals(handTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/hand-tools");
        Assert.assertEquals(powerTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/power-tools");
        Assert.assertEquals(otherTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/other");
        Assert.assertEquals(specialTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/special-tools");
        Assert.assertEquals(retails.getAttribute("href"), "https://practicesoftwaretesting.com/rentals");

        driver.quit();
    }

    @Test
    public void testHandToolsCheckbox() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),' Hand Tools ')]")));

        driver.findElement(By.xpath("//*[contains(text(),' Hand Tools ')]")).click();

        String[] dataTestValues = {
                " Hammer ",
                " Hand Saw ",
                " Wrench ",
                " Screwdriver ",
                " Pliers ",
                " Chisels ",
                " Measures "
        };

        for (String value : dataTestValues) {
            WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(),'" + value + "')]//input"));

            Assert.assertTrue(checkbox.isSelected());
        }

        driver.quit();
    }

    @Test
    public void testHammerAddToCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),' Hammer ')]")));

        driver.findElement(By.xpath("//label[contains(text(),' Hammer ')]")).click();
        driver.findElement(By.xpath("//h5[contains(text(), ' Thor Hammer ')]")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-to-cart")));

        driver.findElement(By.id("btn-add-to-cart")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-test='nav-cart']")));

        driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='product-title']"), "Thor Hammer "));

        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='product-title']")).getText(), "Thor Hammer ");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@data-test='product-quantity']")).getAttribute("min"), "1");
        Assert.assertEquals(driver.findElement(By.xpath("//td[@data-test='cart-total']")).getText(), "$11.14");

        driver.quit();
    }

    @Test
    public void testDeleteHandSawFromCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        Thread.sleep(2000);

        driver.findElement(By.xpath("//label[contains(text(), ' Hand Saw ')]")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//h5[contains(text(), ' Wood Saw ')]")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[@id='btn-add-to-cart']")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@class='btn btn-danger']")).click();

        Thread.sleep(5000);

        Assert.assertEquals(driver.findElement(By.xpath("//p[@class='ng-star-inserted']")).getText(), "The cart is empty. Nothing to display.");

        driver.quit();
    }

    @Test
    public void searchProduct() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");

        driver.findElement(By.xpath("//a[text()=' Products']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.titleIs("Automation Exercise - All Products"));

        driver.findElement(By.xpath("//input[@id='search_product']")).sendKeys("Blue Top");
        driver.findElement(By.xpath("//button[@id='submit_search']")).click();

        WebElement textResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Searched Products')]")));

        Assert.assertEquals(textResult.getText().toLowerCase(), "searched products");

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='productinfo text-center']/p")).getText(), "Blue Top");

        driver.quit();
      }


    @Test
    @Ignore
    public void testPriceOfBooking()  {
        final int numDays = 3;
        final int expectedTotal = 100 * (numDays + 1) + 25 + 15;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://automationintesting.online/");

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//label[@for='checkout']/following::input[1]"))).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[contains(@class, 'day--selected')]/following-sibling::div[%d]".formatted(numDays)))).click();

        driver.findElement(By.xpath("//button[text()='Check Availability']")).click();

        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Book now'])[1]")));

        Actions actions = new Actions(driver);
        actions.moveToElement(bookButton).click().perform();

        String totalText = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[text()='Total']/following-sibling::span"))).getText();

        Assert.assertEquals(expectedTotal, Integer.parseInt(totalText.substring(1)));
        driver.quit();
    }

    @Test
    public void placeholderTextFieldTest() {
        //arrange
        driver.get("https://seleniumbase.io/demo_page");
        WebElement placeholderField = driver.findElement(By.id("placeholderText"));
        //act
        var text = placeholderField.getAttribute("placeholder");
        //assert
        Assert.assertEquals(text, "Placeholder Text Field");
    }

    @Test
    public void radioButtonSelectedTest(){
        //arrange
        driver.get("https://seleniumbase.io/demo_page");
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
        driver.get("https://seleniumbase.io/demo_page");
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
        driver.get("https://seleniumbase.io/demo_page");
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

}
