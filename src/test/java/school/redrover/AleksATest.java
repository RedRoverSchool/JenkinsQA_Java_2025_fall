package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AleksATest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/");
        driver.manage().window().maximize();
    }

    @Test
    public void websiteTextSearch() {
        String searchText = "Selenium WebDriver";
        String expectedText = "Getting started";
        WebElement magnifierField = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='docsearch-1']/button/span[1]/span")));
        magnifierField.click();

        WebElement searchField = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='docsearch-input']")));
        searchField.sendKeys(searchText);


        WebElement searchResult = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("docsearch-item-0")));
        searchResult.click();

        WebElement leftPaneNavigation = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("m-documentationwebdriver-li")));
        leftPaneNavigation.click();

        WebElement hyperlink = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//h5/a")));
        hyperlink.click();

        WebElement titleText = driver.findElement(By.xpath("/html/body/div/div[1]/div/main/div/h1"));

        Assert.assertEquals(titleText.getText(), expectedText);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}