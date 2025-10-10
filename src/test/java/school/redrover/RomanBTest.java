package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class RomanBTest {
    protected WebDriver driver;

    @BeforeMethod
    public WebDriver setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
        driver.get("http://uitestingplayground.com/");

        return driver;
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
        public final WebDriver driver;

        public BasePage(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
        }
    }

    public static class HomePage extends BasePage {
        public HomePage(WebDriver driver) {
            super(driver);
        }

        @FindBy(xpath = "//a[@href='/dynamicid']")
        WebElement dynamicIdLink;

        public <T> T goToPage(String name, T page) {
            driver.findElement(By.xpath(String.format("//h3//a[text()= '%s']", name))).click();

            return page;
        }
    }

    public static class DynamicIdPage extends BasePage {
        public DynamicIdPage(WebDriver driver) {
            super(driver);
        }

        public String getIdButton() {
            WebElement buttonId = driver.findElement(By.xpath("//button[text()= 'Button with Dynamic ID']"));

            return buttonId.getAttribute("id");
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

        Assert.assertNotEquals(id, idNew);
    }
}
