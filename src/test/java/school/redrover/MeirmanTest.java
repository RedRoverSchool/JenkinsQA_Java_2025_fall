package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class MeirmanTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void loginTest() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.inputLogin("standard_user").inputPassword("secret_sauce").clickLoginButton();
        homePage.testPageLogo("Swag Labs");
        homePage.testPageTitle("Products");
    }

    @Test
    public void loginPageErrorTest(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginButton();
        String errorText = loginPage.getErrorText().getText();
        Assert.assertEquals(errorText,"Epic sadface: Username is required");

    }
}


abstract class BasePage {
    protected WebDriver driver;

    BasePage(WebDriver driver) {
        this.driver = driver;
    }
}


class LoginPage extends BasePage {

    LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By loginInput = By.xpath("//input[@id='user-name']");
    private final By passwordInput = By.xpath("//input[@id='password']");
    private final By loginButton = By.xpath("//input[@id='login-button']");
    private final By loginErrorText = By.xpath("//h3[@data-test='error']");

    public LoginPage inputLogin(String login) {
        driver.findElement(loginInput).clear();
        driver.findElement(loginInput).sendKeys(login);
        return this;
    }

    public LoginPage inputPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    public HomePage clickLoginButton() {
        driver.findElement(loginButton).click();
        return new HomePage(driver);
    }

    public WebElement getErrorText(){
        return driver.findElement(loginErrorText);
    }

}

class HomePage extends BasePage {
    HomePage(WebDriver driver) {
        super(driver);
    }

    private final By pageLogo = By.xpath("//div[@class='app_logo']");
    private final By pageTitle = By.xpath("//div/span[@class='title']");

    private WebElement findPageLogo() {
        return driver.findElement(pageLogo);
    }

    private WebElement findPageTitle() {
        return driver.findElement(pageTitle);
    }

    public void testPageLogo(String logoText) {
        WebElement logo = findPageLogo();
        Assert.assertEquals(logo.getText(), logoText);
    }

    public void testPageTitle(String titleText) {
        WebElement logo = findPageTitle();
        Assert.assertEquals(logo.getText(), titleText);
    }

}