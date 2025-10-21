package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GroupJavaCrackersRedroverTest {

    public static void scrollAndClick(Actions actions, WebElement element) {
        actions.scrollToElement(element).perform();
        actions.scrollByAmount(0, 200).perform();
        if (element.isDisplayed() && element.isEnabled()) {
            actions.moveToElement(element).click().perform();
        }
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://automationexercise.com");
        Thread.sleep(200);

        driver.findElement(By.xpath("//button[@aria-label='Consent']")).click();

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        driver.findElement(By.xpath("//a[@href='#Women']")).click();
        Thread.sleep(100);
        driver.findElement(By.xpath("//a[@href='/category_products/1']")).click();
        scrollAndClick(actions, driver.findElement(By.xpath("//a[@href='/product_details/3']")));
        Thread.sleep(200);
        driver.findElement(By.xpath("//button[@type='button']")).click();
        Thread.sleep(300);

        WebElement message = driver.findElement(By.xpath("//div[@class='modal-body']/p[1]"));
        Assert.assertEquals(message.getText(), "Your product has been added to cart.");

        driver.quit();
    }

    @Test
    public void testRecaptchaValidationMessage() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://demoqa.com/");

        WebElement appPage = driver.findElement(By.xpath("//h5[text()='Book Store Application']"));
        scrollAndClick(actions, appPage);

        WebElement login = driver.findElement(By.xpath("//span[text()='Login']"));
        scrollAndClick(actions, login);

        WebElement newUser = driver.findElement(By.id("newUser"));
        scrollAndClick(actions, newUser);

        driver.findElement(By.id("firstname")).sendKeys("Roman");
        driver.findElement(By.id("lastname")).sendKeys("T");
        driver.findElement(By.id("userName")).sendKeys("romant");
        driver.findElement(By.id("password")).sendKeys("123456");

        WebElement register = driver.findElement(By.id("register"));
        scrollAndClick(actions, register);

        WebElement message = driver.findElement(By.id("output"));
        Assert.assertTrue(message.isDisplayed(), "Please verify reCaptcha to register!");

        driver.quit();
    }

    @Test
    public void testSelenium(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(), "Received!");
        driver.quit();
    }

    @Test
    public void testSwagLabs() {
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.name("login-button")).click();
        WebElement secondTitle = driver.findElement(By.cssSelector(".title"));
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(secondTitle.getText(), "Products");

        driver.quit();
    }

    @Test
    public void testHerokuappOpeningAddRemoveElementsPage(){
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://the-internet.herokuapp.com/");

        driver.findElement(By.linkText("Add/Remove Elements")).click();
        WebElement title = driver.findElement(By.cssSelector("h3"));
        Assert.assertEquals(title.getText(), "Add/Remove Elements");

        driver.quit();
    }

    @Test
    public void testHerokuappAddElements(){
        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://the-internet.herokuapp.com/add_remove_elements/");
        driver.findElement(By.tagName("button")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//button[@class='added-manually']"));
        Assert.assertEquals(elements.size(), 1);

        driver.quit();
    }

    @Test
    public void testButtons() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demoqa.com/buttons");

        Actions actions = new Actions(driver);

        actions.doubleClick(driver.findElement(By.xpath("//button [@id='doubleClickBtn']"))).perform();
        actions.contextClick(driver.findElement(By.xpath("//button [@id='rightClickBtn']"))).perform();
        driver.findElement(By.xpath("//button [text()='Click Me']")).click();

        WebElement doubleClick = driver.findElement(By.xpath("//p [@id='doubleClickMessage']"));
        Assert.assertEquals(doubleClick.getText(), "You have done a double click");

        WebElement rightClick = driver.findElement(By.xpath("//p [@id='rightClickMessage']"));
        Assert.assertEquals(rightClick.getText(),"You have done a right click");

        WebElement clickMe = driver.findElement(By.xpath("//p [@id='dynamicClickMessage']"));
        Assert.assertEquals(clickMe.getText(), "You have done a dynamic click");

        driver.quit();
    }


    @Test
    public void testCjCity() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.cjcity.ru");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"nav\"]/li[5]/a"));
        button.click();

        WebElement input = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/div/div[1]/input"));
        input.sendKeys("Luminoforium");

        WebElement button1 = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/div/div[3]/input"));
        button1.click();

        WebElement button2 = driver.findElement(By.xpath("//*[@id=\"content\"]/ul[2]/li/div[2]/span/a"));
        button2.click();

        WebElement img = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div[1]/div/img"));
        Assert.assertEquals(img.getSize().getWidth(), 99);

        driver.quit();

    }


    @Test
    public void testSauceDemo() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com");

        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id='login-button']")).click();

        WebElement message = driver.findElement(By.xpath("//*[@id='item_4_title_link']/div"));

        Assert.assertEquals(message.getText(), "Sauce Labs Backpack");

        driver.quit();
    }

    @Test
    public void testSeleniumDev() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/formPage.html");

        driver.findElement(By.xpath("//*[@id='nested_form']/div/input")).sendKeys("John");
        driver.findElement(By.xpath("//*[@id='nested_form']/input")).click();

        WebElement message = driver.findElement(By.cssSelector("#greeting"));

        Assert.assertEquals(message.getText(), "Success!");

        driver.quit();
    }

    @Test
    public void testAutomationExerciseAddReview() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com");

        WebElement productsButton = driver.findElement(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[2]/a"));
        productsButton.click();
        WebElement viewProductButton = driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div/div[7]/div/div[2]/ul/li/a"));
        viewProductButton.click();

        driver.findElement(By.xpath("//*[@id='name']")).sendKeys("John");
        driver.findElement(By.xpath("//*[@id='email']")).sendKeys("johntest@gmail.com");
        driver.findElement(By.xpath("//*[@id='review']")).sendKeys("Test review text");
        driver.findElement(By.xpath("//*[@id='button-review']")).click();

        WebElement message = driver.findElement(By.xpath("//*[@id='review-section']/div/div/span"));

        Assert.assertEquals(message.getText(), "Thank you for your review.");

        driver.quit();
    }

}
