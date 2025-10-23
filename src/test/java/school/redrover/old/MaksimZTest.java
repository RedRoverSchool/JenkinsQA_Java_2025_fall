package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class MaksimZTest {

    @Test
    public void testRadioButton() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/radio-button");

        WebElement yesRadioButton = driver.findElement(By.cssSelector("label.custom-control-label[for='yesRadio']"));

        yesRadioButton.click();

        driver.quit();

    }

}
