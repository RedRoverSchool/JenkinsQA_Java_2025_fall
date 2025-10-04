package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class OksanaKTest {

    /* Тест проверяет названия radio buttons */

    @Test
    public void testRadioLabels() {
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
