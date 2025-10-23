package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Ignore
public class DmitryOmTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void TestFillingTestBox() {
        driver.get("https://demoqa.com/");

        String name = "Dmitry QA";
        String email = "dmitry.qa@example.com";
        String currentAddress = "Warsaw, Poland";
        String permanentAddress = "Zurawia 16/20, Warszawa, 00-515";

        wait.until(visibilityOfElementLocated(By.xpath("//h5[text()='Elements']"))).click();
        wait.until(visibilityOfElementLocated(By.xpath("//span[text()='Text Box']"))).click();
        wait.until(visibilityOfElementLocated(By.id("userName"))).sendKeys(name);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("currentAddress")).sendKeys(currentAddress);
        driver.findElement(By.id("permanentAddress")).sendKeys(permanentAddress);
        driver.findElement(By.id("submit")).click();

        WebElement outputBox = wait.until(visibilityOfElementLocated(By.id("output")));
        String outputAddress = outputBox.getText();

        Assert.assertTrue(outputAddress.contains(name), "Full name is missing");
        Assert.assertTrue(outputAddress.contains(email), "Email is missing");
        Assert.assertTrue(outputAddress.contains(currentAddress), "Current address is missing");
        Assert.assertTrue(outputAddress.contains(permanentAddress), "Permanent address is missing");
    }

    @Test
    public void TestCheckBoxes() {
        driver.get("https://demoqa.com/checkbox");

        By expandAllBtn = By.cssSelector("button[title='Expand all']");
        By collapseAllBtn = By.cssSelector("button[title='Collapse all']");
        By homeCheckboxIcon = By.cssSelector("label[for='tree-node-home'] .rct-checkbox");
        By leafCheckboxInputs = By.cssSelector(".rct-node-leaf input[type='checkbox']");
        By allTitlesDisplayed = By.cssSelector(".rct-node .rct-title");

        wait.until(elementToBeClickable(expandAllBtn)).click();

        List<WebElement> leafInputs = wait.until(presenceOfAllElementsLocatedBy(leafCheckboxInputs));
        Assert.assertTrue(leafInputs.size() >= 6, "Leaves not present after expand");

        wait.until(elementToBeClickable(homeCheckboxIcon)).click();

        leafInputs = driver.findElements(leafCheckboxInputs);
        boolean allSelected = leafInputs.stream().allMatch(WebElement::isSelected);
        Assert.assertTrue(allSelected, "Not all leaf checkboxes are selected");

        wait.until(elementToBeClickable(homeCheckboxIcon)).click();

        leafInputs = driver.findElements(leafCheckboxInputs);
        boolean noneSelected = leafInputs.stream().noneMatch(WebElement::isSelected);
        Assert.assertTrue(noneSelected, "Some leaf checkboxes remain selected");

        wait.until(elementToBeClickable(collapseAllBtn)).click();

        List<WebElement> visibleAfterCollapse = driver.findElements(allTitlesDisplayed)
                .stream().filter(WebElement::isDisplayed).toList();
        Assert.assertEquals(visibleAfterCollapse.size(), 1, "Tree did not collapse to a single node");
        Assert.assertEquals(visibleAfterCollapse.get(0).getText().trim(), "Home");

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

