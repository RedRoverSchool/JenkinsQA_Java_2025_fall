package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BelyaevVTest {

    private static final String BASE_URL = "https://demoqa.com/";

    @Test
    public void testDoubleClick() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://thecode.media/");

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        new Actions(driver).doubleClick(searchArea).perform();

        String foundSearchTitle = driver.findElement(By.className("search__title")).getText();

        Assert.assertEquals(foundSearchTitle, "Как решить");

        driver.quit();
    }

    @Test
    public void testAddCompareProduct() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://naveenautomationlabs.com/opencart/");

        driver.findElement(By.linkText("Cameras")).click();

        WebElement cameraCanon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='30']"));
        cameraCanon.click();

        Thread.sleep(1000);

        WebElement cameraNikon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='31']"));
        cameraNikon.click();

        Thread.sleep(1000);

        driver.findElement(By.linkText("Product Compare (2)")).click();

        String titleCanon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[2]/a")).getText();
        String titleNikon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[3]/a")).getText();

        String compareCameras = titleCanon + " " + titleNikon;

        Assert.assertEquals(compareCameras, "Canon EOS 5D Nikon D300");

        driver.quit();
    }

    @Test
    public void testCheckOpenNewBrowserWindow() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        String demoqaWindow = driver.getWindowHandle();

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();

        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();

        driver.findElement(By.id("windowButton")).click();

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(demoqaWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        String urlNewWindow =  driver.findElement(By.id("sampleHeading")).getText();

        Assert.assertEquals(urlNewWindow, "This is a sample page");

        driver.quit();
    }

    @Test
    public void testCheckOpenAlertOK() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();
        driver.findElement(By.xpath("//span[text()='Alerts']")).click();
        driver.findElement(By.id("confirmButton")).click();

        Alert alert = driver.switchTo().alert();
        alert.accept();

        String confirmOK = driver.findElement(By.id("confirmResult")).getText();

        Assert.assertEquals(confirmOK, "You selected Ok");

        driver.quit();
    }

    @Test
    public void testClickRightMouseButton(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]")).click();
        driver.findElement(By.xpath("//span[text()='Buttons']")).click();

        WebElement rightClickButton = driver.findElement(By.id("rightClickBtn"));
        new Actions(driver).contextClick(rightClickButton).perform();

        String textMessage = driver.findElement(By.id("rightClickMessage")).getText();
        Assert.assertTrue(textMessage.contains("right click"));

        driver.quit();
    }

    @Test
    public void testFullOrderProduct(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");

        driver.findElement(By.linkText("Sauce Labs Backpack")).click();

        driver.findElement(By.className("btn_primary")).click();
        driver.findElement(By.className("shopping_cart_container")).click();
        driver.findElement(By.className("checkout_button")).click();

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Smith");
        driver.findElement(By.id("postal-code")).sendKeys("123456");

        driver.findElement(By.className("cart_button")).click();
        driver.findElement(By.className("btn_action")).click();

        String orderMessage = driver.findElement(By.className("complete-header")).getText();

        Assert.assertEquals(orderMessage, "THANK YOU FOR YOUR ORDER");

        driver.quit();
    }

}
