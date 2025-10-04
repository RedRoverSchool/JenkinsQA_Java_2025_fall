package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DinaraTest {

    @Test
    public void placeholderTextFieldTest() {
        //arrange
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumbase.io/demo_page");
        WebElement placeholderField = driver.findElement(By.id("placeholderText"));
        //act
        var text = placeholderField.getAttribute("placeholder");
        //assert
        Assert.assertEquals(text, "Placeholder Text Field");
        driver.quit();
    }

    @Test
    public void radioButtonSelectedTest(){
        //arrange
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumbase.io/demo_page");
        WebElement radioButton = driver.findElement(By.id("radioButton2"));
        //assert 1
        Assert.assertFalse(radioButton.isSelected());
        //act
        radioButton.click();
        //assert 2
        Assert.assertTrue(radioButton.isEnabled());
        driver.quit();
    }
    @Test
    public void progressBarStatusTest(){
        //arrange
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumbase.io/demo_page");
        WebElement progressBarStatus = driver.findElement(By.id("progressBar"));
        WebElement progressBarLabel = driver.findElement(By.id("progressLabel"));
        //assert
        Assert.assertEquals(progressBarLabel.getText(), "Progress Bar: (50%)");
        Assert.assertEquals(progressBarStatus.getAttribute("value"), "50");
        driver.quit();
    }

    @Test
    public void dropdownMenuOnHoverTest(){
        //arrange
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumbase.io/demo_page");
        WebElement hoverDropdown = driver.findElement(By.id("myDropdown"));
        //act
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverDropdown).perform();
        //assert
        WebElement dropdownMenu = driver.findElement(By.className("dropdown-content"));
        assert dropdownMenu.getText().contains("Link One");
        assert dropdownMenu.getText().contains("Link Two");
        assert dropdownMenu.getText().contains("Link Three");
        driver.quit();
    }


}
