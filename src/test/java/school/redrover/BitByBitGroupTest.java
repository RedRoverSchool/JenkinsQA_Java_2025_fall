package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.sql.Driver;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BitByBitGroupTest {

    private WebDriver driver;
    private static final String AUTOEX_URL = "https://automationexercise.com";
    private static final String USERNAME = "User_1";
    private static final String EMAIL = "user@gmail.com";
    private static final String SUBJECT = "subject";
    private static final String MESSAGE = "Hello World";


    @BeforeMethod
    public void startBeforeTest() {
        driver = new ChromeDriver();
    }

    @Test
    public void testContactUsSubmit() {
        driver.get(AUTOEX_URL);
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//a[text()=' Contact us']")).click();

        driver.findElement(By.name("name")).sendKeys(USERNAME);
        driver.findElement(By.name("email")).sendKeys(EMAIL);
        driver.findElement(By.name("subject")).sendKeys(SUBJECT);
        driver.findElement(By.name("message")).sendKeys(MESSAGE);

        // Remove all **iframes**, **popups**, **overlays**, and **ads**.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelectorAll('iframe, .popup, .overlay, .ads').forEach(e => e.remove());");


        driver.findElement(By.name("submit")).click();
        driver.switchTo().alert().accept();

        WebElement alertSuccess = driver.findElement(By.xpath("//div[contains(@class, 'alert-success')]"));
        Assert.assertEquals(alertSuccess.getText(),"Success! Your details have been submitted successfully.");

        driver.quit();
    }

    @Test
    public void testLoginFielld() {
        driver.get(AUTOEX_URL);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[4]/a/i"));

        loginButton.click();

        WebElement loginField = driver.findElement(By.cssSelector("[data-qa='login-email']"));
        loginField.sendKeys("hellomail.com");

        String validationMessage = loginField.getAttribute("validationMessage");

        WebElement loginButton2 = driver.findElement(By.cssSelector("[data-qa='login-button']"));
        loginButton2.click();

        Assert.assertTrue(validationMessage.contains("Please include an '@'"),
                "Expected validation message not displayed!");

        driver.quit();

    }

    @Test
    public void testVisible() {

        driver.get(AUTOEX_URL);

        Assert.assertTrue(driver.getTitle().equals("Automation Exercise"));

        WebElement testCasesButton = driver.findElement(By.xpath("//a[contains(text(),'Test Cases')]"));

        testCasesButton.click();

        Assert.assertTrue(driver.getTitle().equals("Automation Practice Website for UI Testing - Test Cases"));

        driver.quit();
    }

    @Test
    public void testCategoriesDropdownMenuLinks() {

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

        driver.get("https://practicesoftwaretesting.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(), ' Hand Saw ')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[contains(text(), ' Wood Saw ')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='btn-add-to-cart']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-test='nav-cart']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-danger']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='ng-star-inserted']")));

        Assert.assertEquals(driver.findElement(By.xpath("//p[@class='ng-star-inserted']")).getText(), "The cart is empty. Nothing to display.");

        driver.quit();
    }

    @Test
    public void searchProduct() {

        driver.get(AUTOEX_URL);

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
        final int numDays = 15;
        final int expectedTotal = 100 * numDays + 25 + 15;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://automationintesting.online/");

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//label[@for='checkout']/following::input[1]"))).click();

        selectDate(numDays);

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
    public void testAddElement(){

        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//a[contains(text(), 'Add/Remove Elements')]")).click();
        driver.findElement(By.xpath("//button[@onclick='addElement()']")).click();

        String result = driver.findElement(By.xpath("//div[@id=\"elements\"]")).getText();

        Assert.assertTrue(result.contains("Delete"), "Text is not found");

        driver.quit();
    }

    @Test
    public void testContextMenu(){

        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();
        Actions actions = new Actions(driver);

        driver.findElement(By.xpath("//a[contains(@href, 'context')]")).click();

        WebElement boxToCallContextMenu = driver.findElement(By.id("hot-spot"));
        actions.contextClick(boxToCallContextMenu).perform();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(), "You selected a context menu",
                "Context menu did not show up");

        driver.quit();
    }


    private void selectDate(int daysToAdd) {
        LocalDate today = LocalDate.now();
        LocalDate targetDay = today.plusDays(daysToAdd);

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(("d"));

        while(true) {
            String currentMonth = driver.findElement(By.className("react-datepicker__current-month")).getText();

            if (targetDay.format(monthFormatter).equalsIgnoreCase(currentMonth)) {
                String targetDayString = targetDay.format(dayFormatter);
                driver.findElement(By.className("react-datepicker__day--0%s".formatted(targetDayString))).click();
                break;
            } else {
                driver.findElement(By.className("react-datepicker__navigation--next")).click();
            }
        }
    }

    @Test
    public void testDropDown() {

        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//a[contains(@href, 'dropdown')]")).click();
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));

        List<String> expectedValues = List.of("Please select an option", "Option 1", "Option 2");
        List<String> actualValues = dropdown.getOptions()
                .stream().map(WebElement::getText).map(String::trim).toList();

        Assert.assertEquals(actualValues, expectedValues);

        driver.quit();

    }

    @Test
    public void testGetConcerts() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ticketmaster.com/discover/washington-dc");

        WebElement concertsButton = driver.findElement(By.xpath("//a[@data-testid='Concerts']"));
        concertsButton.click();
        Thread.sleep(1200);

        WebElement seeAllConcertsButton = driver.findElement(By.xpath("//*[@id=\":r1:-Concerts\"]/div[1]/div/a"));
        seeAllConcertsButton.click();

        WebElement concertTickets = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/div/h1"));
        Assert.assertEquals(concertTickets.getText(), "CONCERT TICKETS");

        driver.quit();
    }


    @Test
    public void filterWork() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.ticketmaster.com/discover/washington-dc");

        WebElement concertsButton = driver.findElement(By.xpath("//a[@data-testid='Concerts']"));
        concertsButton.click();
        Thread.sleep(1200);

        WebElement cookies = driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]"));
        cookies.click();
        Thread.sleep(1000);

        WebElement seeAllConcertsButton = driver.findElement(By.xpath("//*[@id=\":r1:-Concerts\"]/div[1]/div/a"));
        seeAllConcertsButton.click();

        WebElement dropdownButton = driver.findElement(By.xpath("//button[@data-testid='category-filter']"));
        dropdownButton.click();
        Thread.sleep(2000);

        WebElement bluesGenreButton = driver.findElement(By.xpath("//*[@id=\":Radam:\"]/li[4]/a"));

        Actions actions = new Actions(driver);
        actions.scrollToElement(bluesGenreButton).perform();

        bluesGenreButton.click();
        Thread.sleep(2000);

        WebElement bluesGenre = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/div/h1"));
        Assert.assertEquals(bluesGenre.getText(), "BLUES");

        driver.quit();
    }
    @Test
}
