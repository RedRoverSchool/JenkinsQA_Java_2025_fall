package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class BelyaevVTest {

    private static final String BASE_URL = "https://demoqa.com/";
    private static final String THECODE_URL ="https://thecode.media/";

    @Test
    public void testDoubleClick() {

        WebDriver driver = new ChromeDriver();
        driver.get(THECODE_URL);

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        new Actions(driver).doubleClick(searchArea).perform();

        String foundSearchTitle = driver.findElement(By.className("search__title")).getText();

        Assert.assertEquals(foundSearchTitle, "Как решить");

        driver.quit();
    }

    @Test
    public void testAddCompareProduct() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://naveenautomationlabs.com/opencart/");

        driver.findElement(By.linkText("Cameras")).click();

        WebElement cameraCanon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='30']"));
        cameraCanon.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement cameraNikon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='31']"));
        cameraNikon.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.findElement(By.linkText("Product Compare (2)")).click();

        String titleCanon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[2]/a")).getText();
        String titleNikon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[3]/a")).getText();

        String compareCameras = titleCanon + " " + titleNikon;

        Assert.assertEquals(compareCameras, "Canon EOS 5D Nikon D300");

        driver.quit();
    }

    //    checks switch between tabs and windows

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

        String newWindow =  driver.findElement(By.id("sampleHeading")).getText();

        Assert.assertEquals(newWindow, "This is a sample page");

        driver.quit();
    }

    @Test
    public void testCheckSwitchNewTab() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(BASE_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();
        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();
        driver.findElement(By.id("tabButton")).click();

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);

        String newTab =  driver.findElement(By.id("sampleHeading")).getText();

        Assert.assertEquals(newTab, "This is a sample page");

        driver.quit();
    }

    @Test
    public void testCheckSwitchBackToMainWindow() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(BASE_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();
        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();
        driver.findElement(By.id("tabButton")).click();

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        driver.close();
        driver.switchTo().window((String) windowHandles[0]);

        String titleMainSite = driver.getTitle();

        Assert.assertEquals(titleMainSite, "DEMOQA");

        driver.quit();
    }

    @Test
    public void testCheckSwitchNewTabUseTab() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(THECODE_URL);
        String thecodeWindow = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(THECODE_URL + "howto/");

        String titleOpenTab =  driver.findElement(By.className("search__title")).getText();

        Assert.assertEquals(titleOpenTab, "Это как");

        driver.close();
        driver.switchTo().window((thecodeWindow));
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

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[1]")).click();
        driver.findElement(By.xpath("//span[text()='Buttons']")).click();

        WebElement rightClickButton = driver.findElement(By.id("rightClickBtn"));
        new Actions(driver).contextClick(rightClickButton).perform();

        String textMessage = driver.findElement(By.id("rightClickMessage")).getText();
        Assert.assertTrue(textMessage.contains("right click"));

        driver.quit();
    }

    @Test
    public void testCheckFieldToolTip() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(BASE_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[4]")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight/2);");

        driver.findElement(By.xpath("//span[text()='Tool Tips']")).click();

        new Actions(driver).moveToElement(driver.findElement(By.id("toolTipTextField"))).perform();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        String hoverText = driver.findElement(By.id("textFieldToolTip")).getText();

        Assert.assertEquals(hoverText, "You hovered over the text field");

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
