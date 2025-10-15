package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupUnitedByJavaTest {

    @Test
    public void testDoubleClick() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://demoqa.com/");

        WebElement elementsPage = driver.findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = driver.findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe = driver.findElement(By.id("doubleClickBtn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickMe);
        actions.perform();

        String doubleClickMessage = driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessage, "You have done a double click");

        driver.quit();
    }
}
