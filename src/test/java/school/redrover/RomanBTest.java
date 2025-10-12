package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.Duration;

import org.testng.annotations.Test;

@Listeners(RomanBTest.TestListener.class)
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

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static class TestListener implements ITestListener {
        String message = "";
        @Override
        public void onTestStart(ITestResult result) {
            message = "🚀 TEST STARTED: " + result.getName() ;
            Reporter.log(message + "<br>");
            System.out.println(message);
        }

        @Override
        public void onTestSuccess(ITestResult result) {
            message = "✅ TEST PASSED: " + result.getName();
            Reporter.log(message + "<br>");
            System.out.println(message);
        }

        @Override
        public void onTestFailure(ITestResult result) {
            Reporter.log("<b>❌ FAILED:</b> " + result.getName() + "<br>");
            Reporter.log("Cause: " + result.getThrowable() + "<br>");
            message = "❌ TEST FAILED: " + result.getName();
            System.out.println(message);

            Object testClass = result.getInstance();
            try {
                WebDriver driver = (WebDriver) testClass
                        .getClass()
                        .getDeclaredField("driver")
                        .get(testClass);

                if (driver != null) {
                    TakesScreenshot ts = (TakesScreenshot) driver;
                    String base64 = ts.getScreenshotAs(OutputType.BASE64);
                    String imgTag = "<img src='data:image/png;base64," + base64 + "' height='200'/>";
                    Reporter.log(imgTag + "<br>");
                }
            } catch (Exception e) {
                Reporter.log("⚠️ Не удалось получить WebDriver: " + e.getMessage());
            }
        }

        @Override
        public void onTestSkipped(ITestResult result) {
            Reporter.log("<b>⏩ SKIPPED:</b> " + result.getName() + "<br>");
            message = "⏩ TEST SKIPPED: " + result.getName();
            System.out.println(message);
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
            getDriver().findElement(By.xpath(String.format("//h3//a[text()='%s']", name))).click();
            Reporter.log("Title is : " + page.toString());
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

    public static class HiddenLayersPage extends BasePage {
        public HiddenLayersPage(WebDriver driver) {
            super(driver);
        }



        public String notAllowedClickMoreThanOne() {
            String message = "";
            boolean clickable = true;
            WebElement enableButton = wait4().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id = 'greenButton']")));
            try {
                enableButton.click();
                message = "Green button visible and  clicked. Click : ";
            }
            catch (ElementClickInterceptedException e) {
                message = "Green button is hidden. Click : " ;
                clickable = false;
            }
            return  message + Boolean.toString(clickable);
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

    @Test
    public  void testHiddenLayers() {
        String firstClick = new HomePage(driver)
                .goToPage("Hidden Layers", new HiddenLayersPage(driver))
                .notAllowedClickMoreThanOne();
        String secondClick = new HiddenLayersPage(driver)
                .notAllowedClickMoreThanOne();

        softAssert.assertEquals(firstClick, "Green button visible and  clicked. Click : true");
        softAssert.assertEquals(secondClick, "Green button is hidden. Click : false");
        softAssert.assertAll();
    }
}


