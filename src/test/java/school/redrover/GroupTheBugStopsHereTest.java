package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupTheBugStopsHereTest {
    private WebDriverWait wait;

    @Test
    public void testAddReview() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.automationexercise.com/");
        handleCookies(driver);

        WebElement submitProductsButton = driver.findElement(By.cssSelector("a[href='/products']"));
        submitProductsButton.click();

        String pageProductsTitle = driver.getTitle();
        driver.findElement(By.cssSelector(".title.text-center"));
        Assert.assertEquals(pageProductsTitle, "Automation Exercise - All Products", "Ошибка! Заголовок страницы не совпал с ожидаемым.");

        WebElement viewProductButton = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
        viewProductButton.click();

        WebElement messageReview = driver.findElement(By.cssSelector("a[href='#reviews']"));
        Assert.assertTrue(messageReview.isDisplayed(), "The element should be visible on the page.");

        WebElement nameTextBox = driver.findElement(By.cssSelector("#name"));
        nameTextBox.sendKeys("username");

        WebElement emailTextBox = driver.findElement(By.cssSelector("#email"));
        emailTextBox.sendKeys("email@test.ru");

        WebElement reviewTextBox = driver.findElement(By.cssSelector("#review"));
        reviewTextBox.sendKeys("test review text");

        WebElement reviewSubmitButton = driver.findElement(By.cssSelector("#button-review"));
        reviewSubmitButton.click();

        WebElement element = driver.findElement(By.cssSelector("#review-section .alert-success.alert"));
        Assert.assertTrue(element.isDisplayed(), "The element should be visible on the page.");

        driver.quit();
    }

    @Test
    public void testAutomationExercise() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/test_cases");
        handleCookies(driver);
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("ememdems@hotmail.com");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")).sendKeys("8XbTY@zG@wYg2hg");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a")).isDisplayed());

        driver.quit();
    }

    @Test
    public void registerUserTest(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.automationexercise.com");
        handleCookies(driver);

        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

        driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]")).click();

        WebElement newUserSignup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'New User Signup!')]")));
        Assert.assertTrue(newUserSignup.isDisplayed(), "'New User Signup!' is not visible");

        String uniqueEmail = "alex" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.name("name")).sendKeys("AlexTest");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(uniqueEmail);

        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        WebElement enterAccountInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[contains(text(),'Enter Account Information')]")));
        Assert.assertTrue(enterAccountInfo.isDisplayed(), "'ENTER ACCOUNT INFORMATION' is not visible");

        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        new Select(driver.findElement(By.id("days"))).selectByValue("10");
        new Select(driver.findElement(By.id("months"))).selectByValue("5");
        new Select(driver.findElement(By.id("years"))).selectByValue("1995");

        driver.findElement(By.id("newsletter")).click();

        driver.findElement(By.id("optin")).click();

        driver.findElement(By.id("first_name")).sendKeys("Alex");
        driver.findElement(By.id("last_name")).sendKeys("Tester");
        driver.findElement(By.id("company")).sendKeys("Automation Inc");
        driver.findElement(By.id("address1")).sendKeys("123 Test Street");
        driver.findElement(By.id("address2")).sendKeys("Apt 45");
        new Select(driver.findElement(By.id("country"))).selectByVisibleText("Canada");
        driver.findElement(By.id("state")).sendKeys("Ontario");
        driver.findElement(By.id("city")).sendKeys("Toronto");
        driver.findElement(By.id("zipcode")).sendKeys("A1B2C3");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");

        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        WebElement accountCreated = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[contains(text(),'Account Created!')]")));
        Assert.assertTrue(accountCreated.isDisplayed(), "'ACCOUNT CREATED!' is not visible");

        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Logged in as')]")));
        WebElement loggedIn = driver.findElement(By.xpath("//a[contains(text(),'Logged in as')]"));
        Assert.assertTrue(loggedIn.isDisplayed(), "'Logged in as username' is not visible");

        driver.findElement(By.xpath("//a[contains(text(),'Delete Account')]")).click();

        WebElement accountDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[contains(text(),'Account Deleted!')]")));
        Assert.assertTrue(accountDeleted.isDisplayed(), "'ACCOUNT DELETED!' is not visible");
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        driver.quit();
    }

    @Test
    public void testPositiveLogin() {
        WebDriver driver = new ChromeDriver();
        driver = new ChromeDriver();
        driver.get("https://www.automationexercise.com/");
        handleCookies(driver);
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.name("email")).sendKeys("veshkvarok@mail.ru");
        driver.findElement(By.name("password")).sendKeys("Questiov!#13");
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//a[@href='/logout']")).getText(), "Logout");

        driver.quit();
    }

    @Test
    public void testLogin() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        handleCookies(driver);
        driver.manage().window().maximize();
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("logInModalLabel")));
        driver.findElement(By.xpath("//*[@id='loginusername']")).sendKeys("mTest@gmail.com");
        driver.findElement(By.xpath("//*[@id='loginpassword']")).sendKeys("45784okng_75()");
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='logout2']")).isDisplayed(),
                "Fail");
        driver.quit();
    }

    @Test
    public void loginUserIncorrect(){

        WebDriver driver = new ChromeDriver();

        driver.get("http://automationexercise.com");
        handleCookies(driver);
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("123@email");
        driver.findElement(By.name("password")).sendKeys("#$");

        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        WebElement message = driver.findElement(By.xpath("//*[@id='form']//div[1]//p"));
        Assert.assertEquals(message.getText(), "Your email or password is incorrect!");

        driver.quit();
    }

    private void handleCookies(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieAcceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[p[contains(text(),'Consent')] or contains(.,'Consent') or contains(.,'Accept') or contains(.,'Agree')]")
            ));
            cookieAcceptButton.click();
            System.out.println("✅ Cookie consent accepted.");
            wait.until(ExpectedConditions.invisibilityOf(cookieAcceptButton));
        } catch (Exception e) {
            System.out.println("ℹ️ No cookie consent dialog found. Continuing test.");
        }
    }

    @Test
    public void testLoginWithValidCredentials() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.automationexercise.com/");

        driver.findElement(By.xpath("//button[@aria-label='Соглашаюсь']")).click();

        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

        driver.findElement(By.xpath("//a[@href='/login']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='Login to your account']")).isDisplayed());

        driver.quit();

    }

    private WebDriver driver;
    private static final String BASE_URL = "http://automationexercise.com";

    @BeforeSuite
    public void setDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterSuite
    public void closeDriver(){
        driver.quit();
    }

    @Test
    public void testVerifyDetailOfProductVisible() {
        WebElement consentButton = driver.findElement(By.xpath("//button/*[contains(text(),'Consent')]"));
        consentButton.click();

        String titleHomePage = driver.getTitle();
        Assert.assertEquals(titleHomePage, "Automation Exercise");

        WebElement productsButton = driver.findElement(By.xpath("//a[@href='/products']"));
        productsButton.click();

        String titleProductsPage = driver.getTitle();
        Assert.assertEquals(titleProductsPage, "Automation Exercise - All Products");

        WebElement headerAllProducts = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        String headerAllProductsText = headerAllProducts.getText();
        Assert.assertTrue(headerAllProducts.isDisplayed());
        Assert.assertEquals(headerAllProductsText, "ALL PRODUCTS");

        WebElement productList = driver.findElement(By.xpath("//div[@class='features_items']"));
        Assert.assertTrue(productList.isDisplayed());

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        WebElement firstViewProductLink = driver.findElement(By.xpath("(//div[@class='choose']//a)[1]"));
        firstViewProductLink.click();

        WebElement nameProduct = driver.findElement(By.xpath("//div[@class='product-information']/h2"));
        Assert.assertTrue(nameProduct.isDisplayed());
    }
}