package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

@Ignore
public class GroupFutureJavaExpertsTest {
    @Test
    public void testCheckBox(){
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/checkbox");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.cssSelector(".rct-checkbox svg")).click();

        WebElement resultMSG = driver.findElement(By.id("result"));
        Assert.assertTrue(resultMSG.isDisplayed());

        driver.quit();
    }

    @Test
    public void testRadioBtnLabels() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/radio-button#google_vignette");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Yes");
        expected.add("Impressive");
        expected.add("No");

        WebElement yesLabel = driver.findElement(By.xpath("//label[@for='yesRadio']"));
        WebElement impressiveLabel = driver.findElement(By.xpath("//label[@for='impressiveRadio']"));
        WebElement noLabel = driver.findElement(By.xpath("//label[@for='noRadio']"));

        ArrayList<String> actual = new ArrayList<>();
        actual.add(yesLabel.getText());
        actual.add(impressiveLabel.getText());
        actual.add(noLabel.getText());

        Assert.assertEquals(actual, expected, "Label is incorrect");

        driver.quit();
    }
}
