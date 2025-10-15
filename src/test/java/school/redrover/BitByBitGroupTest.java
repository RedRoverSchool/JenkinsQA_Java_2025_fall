package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class BitByBitGroupTest {

    private WebDriver driver;

    @BeforeMethod
    public void startBeforeTest() {
        driver = new ChromeDriver();
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
}
