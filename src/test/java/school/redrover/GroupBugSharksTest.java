package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GroupBugSharksTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String PASSWORD = "secret_sauce";
    private static final String BASE_URL_SD = "https://www.saucedemo.com";
    private static final String PRODUCT_PAGE_URL_SD = BASE_URL_SD + "/inventory.html";
    private static final String USERNAME = "yar"+ System.currentTimeMillis();
    private static final String USEREMAIL = USERNAME + "@gmail.com";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void openSauceDemoHomePage() {
        driver.get(BASE_URL_SD);
    }

    private void automationexercise() {
        driver.get("https://automationexercise.com/");
    }

    @Test
    public void testSuccessfulLoginPos() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//h6[text()='Dashboard']")).getText(),
                "Dashboard");
    }

    @Test
    public void testWrongCredentialsLoginNeg() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("wrongLogin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("wrongPassword");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//p[text()='Invalid credentials']"))
                .getText(), "Invalid credentials");
    }

    @Test
    public void testEmptyCredentialsLoginNeg() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By
                        .xpath("//label[text()='Username']/ancestor::div/span[text()='Required']")).getText(),
                "Required");
        Assert.assertEquals(driver.findElement(By
                        .xpath("//label[text()='Password']/ancestor::div/span[text()='Required']")).getText(),
                "Required");
    }

    @Test
    public void testSauceDemoHomePageTitle() {
        openSauceDemoHomePage();

        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, "Swag Labs");
    }

    @DataProvider(name = "userCredentials")
    public String[] provideUserCredentials() {
        return new String[]{
                "standard_user",
                "performance_glitch_user"
        };
    }

    @Test(dataProvider = "userCredentials")
    public void testSauceDemoSuccessfulLogin(String username) {
        openSauceDemoHomePage();

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("login-button")).click();

        WebElement appLogo = driver.findElement(By.className("app_logo"));

        Assert.assertEquals(appLogo.getText(), "Swag Labs");
    }

    @Test
    public void testProductsButton() {
        automationexercise();
        WebElement products = driver.findElement(By.xpath("//a[@href='/products']"));

        Assert.assertEquals(products.getText(), " Products");
    }

    @Test
    public void testEmail() {
        automationexercise();
        WebElement cart = driver.findElement(By.xpath("//a[@href='/view_cart']"));
        cart.click();

        WebElement field = driver.findElement(By.id("susbscribe_email"));
        field.sendKeys("Ted@gmail.com");

        WebElement go = driver.findElement(By.cssSelector(".fa.fa-arrow-circle-o-right"));
        go.click();

        WebElement successfulMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-success.alert")));

        Assert.assertEquals(successfulMessage.getText(), "You have been successfully subscribed!");
    }
    @Test
    public void testLogIn() throws InterruptedException {
        automationexercise();
        Thread.sleep(100);
        WebElement signUplogInButton = driver.findElement(By.xpath("//a[@href='/login']"));
        signUplogInButton.click();

        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//form//input[@data-qa='signup-name']")));
        loginField.sendKeys(USERNAME);

        WebElement userPassword = driver.findElement(By.xpath("//form//input[@data-qa='signup-email']"));
        userPassword.sendKeys(USEREMAIL);

        WebElement signUpButton = driver.findElement(By.xpath("//form//button[@data-qa='signup-button']"));
        signUpButton.click();

        WebElement createButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//form//button[@data-qa='create-account']")));

        Assert.assertEquals(createButton.getText(),"Create Account");
    }

    public void loginStandartUser() {
        openSauceDemoHomePage();

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("login-button")).click();
    }

    @Test
    public void testCorrectProductsPage() {
        loginStandartUser();

        WebElement titleProductPage = driver.findElement(By.xpath(".//div[@id=\"header_container\"]/div[2]/span"));

        Assert.assertEquals(driver.getTitle(), "Swag Labs");
        Assert.assertEquals(driver.getCurrentUrl(), PRODUCT_PAGE_URL_SD);
        Assert.assertEquals(titleProductPage.getText(), "Products");
    }

    @Test
    public void testBackToProductsButton() {
        loginStandartUser();

        WebElement productName = driver.findElement(By.xpath(".//*[@id=\"item_2_title_link\"]/div"));
        productName.click();

        WebElement backToProductsButton = driver.findElement(By.id("back-to-products"));
        Assert.assertEquals(backToProductsButton.getText(), "Back to products");

        backToProductsButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), PRODUCT_PAGE_URL_SD);
    }

    @Test
    public void testCorrectAllProductName() {
        loginStandartUser();

        List<WebElement> productNamesList = driver.findElements(By.xpath(".//div[@class=\"inventory_item_name \"]"));

        List<String> namesList = productNamesList.stream()
                .map(product -> product.getText())
                .toList();

        List<String> currentNamesList = new ArrayList<>();

        for (int i = 0; i < productNamesList.size(); i++) {
            productNamesList = driver.findElements(By.xpath(".//div[@class=\"inventory_item_name \"]"));
            productNamesList.get(i).click();

            WebElement currentName = driver.findElement(By.xpath(".//div[@class=\"inventory_details_desc_container\"]/div"));
            currentNamesList.add(currentName.getText());

            driver.findElement(By.id("back-to-products")).click();
        }
        Assert.assertEquals(namesList, currentNamesList);
    }

    @Test
    public void testCorrectProductPrice() {
        loginStandartUser();

        String priceByProductPage = driver.findElement(By
                        .xpath(".//*[@id=\"item_2_title_link\"]/ancestor::div[@class=\"inventory_item_description\"]//div[@class=\"pricebar\"]/div"))
                .getText();

        WebElement productName = driver.findElement(By.xpath(".//*[@id=\"item_2_title_link\"]/div"));
        productName.click();

        String priceByItemPage = driver.findElement(By
                        .xpath(".//div[@class=\"inventory_details_price\"]"))
                .getText();

        Assert.assertEquals(priceByItemPage, priceByProductPage);
    }

    @Test
    public void testCorrectProductImageTest() {
        loginStandartUser();

        String imageByProductPage = driver.findElement(By
                        .xpath(".//*[@id=\"item_2_img_link\"]/img"))
                .getAttribute("src");

        WebElement productName = driver.findElement(By.xpath(".//*[@id=\"item_2_title_link\"]/div"));
        productName.click();

        String imageByItemPage = driver.findElement(By
                        .xpath(".//div[@class=\"inventory_details_img_container\"]/img"))
                .getAttribute("src");

        Assert.assertEquals(imageByItemPage, imageByProductPage);
    }

    @Test
    public void artemTLoginTest() {
        String baseUrl = "https://www.saucedemo.com";
        String standardUserName = "standard_user";
        String password = "secret_sauce";
        String plpUrl = "https://www.saucedemo.com/inventory.html";

        driver.get(baseUrl);

        WebElement userNameInputField = driver.findElement(By.id("user-name"));
        WebElement passwordInputField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        userNameInputField.sendKeys(standardUserName);
        passwordInputField.sendKeys(password);
        loginButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), plpUrl, "The actual URL is not equal to expected");

        WebElement plpHeader = driver.findElement(By.id("header_container"));
        WebElement mainMenuButton = driver.findElement(By.id("react-burger-menu-btn"));
        WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));

        Assert.assertTrue(plpHeader.isDisplayed(), "The header element is not visible");
        Assert.assertTrue(mainMenuButton.isDisplayed(), "The main menu button element is not visible");
        Assert.assertTrue(shoppingCart.isDisplayed(), "The shopping cart element is not visible");
    }
}
