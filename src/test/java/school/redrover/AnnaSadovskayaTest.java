package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class AnnaSadovskayaTest {

    @Test
    public void testCategoriesDropdownMenuLinks() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//a[@data-test='nav-categories']")).click();

        WebElement handTools = driver.findElement(By.xpath("//a[@data-test='nav-hand-tools']"));
        Assert.assertEquals(handTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/hand-tools");

        WebElement powerTools = driver.findElement(By.xpath("//a[@data-test='nav-power-tools']"));
        Assert.assertEquals(powerTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/power-tools");

        WebElement otherTools = driver.findElement(By.xpath("//a[@data-test='nav-other']"));
        Assert.assertEquals(otherTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/other");

        WebElement specialTools = driver.findElement(By.xpath("//a[contains(text(),'Special Tools')]"));
        Assert.assertEquals(specialTools.getAttribute("href"),"https://practicesoftwaretesting.com/category/special-tools");

        WebElement retails = driver.findElement(By.xpath("//a[contains(text(),'Rentals')]"));
        Assert.assertEquals(retails.getAttribute("href"), "https://practicesoftwaretesting.com/rentals");

        driver.quit();
    }

    @Test
    public void testHandToolsCheckbox() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[contains(text(),' Hand Tools ')]")).click();

        String[] dataTestValues = {
                " Hammer ",
                " Hand Saw ",
                " Wrench ",
                " Screwdriver ",
                " Pliers ",
                " Chisels ",
                " Measures "
        };

        for (String value : dataTestValues) {
            WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(),'" + value + "')]//input"));

            Assert.assertTrue(checkbox.isSelected());
        }

        driver.quit();
    }

    @Test
    public void testHammerAddToCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        Thread.sleep(2000);

        driver.findElement(By.xpath("//label[contains(text(),' Hammer ')]")).click();
        driver.findElement(By.xpath("//h5[contains(text(), ' Thor Hammer ')]")).click();

        Thread.sleep(2000);

        driver.findElement(By.id("btn-add-to-cart")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();

        Thread.sleep(4000);

        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='product-title']")).getText(), "Thor Hammer ");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@data-test='product-quantity']")).getAttribute("min"), "1");
        Assert.assertEquals(driver.findElement(By.xpath("//td[@data-test='cart-total']")).getText(), "$11.14");

        driver.quit();
    }

    @Test
    public void testDeleteHandSawFromCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicesoftwaretesting.com/");

        Thread.sleep(2000);

        driver.findElement(By.xpath("//label[contains(text(), ' Hand Saw ')]")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//h5[contains(text(), ' Wood Saw ')]")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[@id='btn-add-to-cart']")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@class='btn btn-danger']")).click();

        Thread.sleep(5000);

        Assert.assertEquals(driver.findElement(By.xpath("//p[@class='ng-star-inserted']")).getText(), "The cart is empty. Nothing to display.");

        driver.quit();
    }

    @Test
    public void searchProduct() {
        /*
         Launch browser
        2. Navigate to url 'http://automationexercise.com'
        3. Verify that home page is visible successfully
        4. Click on 'Products' button
        5. Verify user is navigated to ALL PRODUCTS page successfully
        6. Enter product name in search input and click search button
        7. Verify 'SEARCHED PRODUCTS' is visible
        8. Verify all the products related to search are visible
         */

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");

        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        driver.findElement(By.xpath("//a[text()=' Products']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.titleIs("Automation Exercise - All Products"));

        Assert.assertEquals(driver.getTitle(),"Automation Exercise - All Products");

        driver.findElement(By.xpath("//input[@id='search_product']")).sendKeys("Blue Top");
        driver.findElement(By.xpath("//button[@id='submit_search']")).click();

        WebElement textResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Searched Products')]")));

        Assert.assertEquals(textResult.getText().toLowerCase(), "searched products");

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='productinfo text-center']/p")).getText(), "Blue Top");

        driver.quit();
    }
}

