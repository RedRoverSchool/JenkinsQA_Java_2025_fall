package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;



public class RomanBTest {
    protected WebDriver driver;

    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
        driver.get("http://uitestingplayground.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (driver != null) {
            if (testResult.isSuccess()) {
                driver.quit();
            } else {
                System.out.println("Test failed: " + testResult.getName());
                driver.quit();
            }
        }
    }

    public static class BasePage {
        private final WebDriver driver;

        public BasePage(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
        }

        public WebDriver getDriver() {
            return driver;
        }

        public WebDriverWait wait4() {
            return new WebDriverWait(getDriver(), Duration.ofSeconds(4));
        }
    }

    public static class HomePage extends BasePage {
        public HomePage(WebDriver driver) {
            super(driver);
        }

        @FindBy(xpath = "//a[@href='/dynamicid']")
        WebElement dynamicIdLink;

        public <T> T goToPage(String name, T page) {
            getDriver().findElement(By.xpath(String.format("//h3//a[text()= '%s']", name))).click();

            return page;
        }
    }

    public static class DynamicIdPage extends BasePage {
        public DynamicIdPage(WebDriver driver) {
            super(driver);
        }

        public String getIdButton() {
            WebElement buttonId = getDriver().findElement(By.xpath("//button[text()= 'Button with Dynamic ID']"));

            return buttonId.getAttribute("id");
        }
    }

    public static class ClassAttributePage extends BasePage {
        public ClassAttributePage(WebDriver driver) {
            super(driver);
        }

        @FindBy(xpath = "//button[contains(concat(' ', normalize-space(@class), ' ' ),  'btn-primary')]")
        WebElement buttonPrimary;

        public String getAlert() {
            buttonPrimary.click();
            wait4().until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            String text = alert.getText();
            alert.accept();

            return text;
        }

        public String getAttributeClass() {
            return buttonPrimary.getAttribute("class");
        }
    }

    @Test
    public void testDynamicId() {
        String id = new HomePage(driver)
                .goToPage("Dynamic ID", new DynamicIdPage(driver))
                .getIdButton();

        driver.navigate().refresh();

        String idNew = new DynamicIdPage(driver)
                .getIdButton();

        Assert.assertNotEquals(id, idNew, "ID должен меняться после обновления страницы");
    }

    @Test
    public void testClassAttribute() {
        String alert = new HomePage(driver)
                .goToPage("Class Attribute", new ClassAttributePage(driver))
                .getAlert();
        String attributeClassButton = new ClassAttributePage(driver)
                .getAttributeClass();

        softAssert.assertEquals(alert, "Primary button pressed");
        softAssert.assertTrue(attributeClassButton.contains("btn-primary"));
        softAssert.assertAll();
    }
}
