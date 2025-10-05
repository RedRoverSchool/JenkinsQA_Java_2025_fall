package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DinaraTest {
    private final String baseUrl = "https://seleniumbase.io/demo_page";
    private static WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }
    @AfterMethod
    public void tearDown(){
        if (driver!= null) driver.quit();
    }

    @Test
    public void placeholderTextFieldTest() {
        //arrange
        driver.get(baseUrl);
        WebElement placeholderField = driver.findElement(By.id("placeholderText"));
        //act
        var text = placeholderField.getAttribute("placeholder");
        //assert
        Assert.assertEquals(text, "Placeholder Text Field");
    }

    @Test
    public void radioButtonSelectedTest(){
        //arrange
        driver.get(baseUrl);
        WebElement radioButton = driver.findElement(By.id("radioButton2"));
        //assert 1
        Assert.assertFalse(radioButton.isSelected());
        //act
        radioButton.click();
        //assert 2
        Assert.assertTrue(radioButton.isEnabled());
    }
    @Test
    public void progressBarStatusTest(){
        //arrange
        driver.get(baseUrl);
        WebElement progressBarStatus = driver.findElement(By.id("progressBar"));
        WebElement progressBarLabel = driver.findElement(By.id("progressLabel"));
        //assert
        Assert.assertEquals(progressBarLabel.getText(), "Progress Bar: (50%)");
        Assert.assertEquals(progressBarStatus.getAttribute("value"), "50");
    }

    @Test
    public void dropdownMenuOnHoverTest(){
        //arrange
        driver.get(baseUrl);
        WebElement hoverDropdown = driver.findElement(By.id("myDropdown"));
        //act
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverDropdown).perform();
        WebElement dropdownMenu = driver.findElement(By.className("dropdown-content"));
        //assert
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(dropdownMenu.getText().contains("Link One"));
        softAssert.assertTrue(dropdownMenu.getText().contains("Link Two"));
        softAssert.assertTrue(dropdownMenu.getText().contains("Link Three"));
        softAssert.assertAll();
    }


}
