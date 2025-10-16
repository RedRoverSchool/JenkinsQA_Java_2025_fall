package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupTheBugStopsHere {

    @Test
    public void testAddReview() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.automationexercise.com/");

        WebElement submitProductsButton = driver.findElement(By.cssSelector("a[href='/products']"));
        submitProductsButton.click();

        String pageProductsTitle = driver.getTitle();
        driver.findElement(By.cssSelector(".title.text-center"));
        Assert.assertEquals(pageProductsTitle, "Automation Exercise - All Products", "Ошибка! Заголовок страницы не совпал с ожидаемым.");

        WebElement viewProductButton = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
        viewProductButton.click();

        WebElement messageReview = driver.findElement(By.cssSelector("a[href='#reviews']"));
        Assert.assertTrue(messageReview.isDisplayed(), "The element should be visible on the page.");

        WebElement nameTextBox = driver.findElement(By.cssSelector("#name"));
        nameTextBox.sendKeys("username");

        WebElement emailTextBox = driver.findElement(By.cssSelector("#email"));
        emailTextBox.sendKeys("email@test.ru");

        WebElement reviewTextBox = driver.findElement(By.cssSelector("#review"));
        reviewTextBox.sendKeys("test review text");

        WebElement reviewSubmitButton = driver.findElement(By.cssSelector("#button-review"));
        reviewSubmitButton.click();

        WebElement element = driver.findElement(By.cssSelector("#review-section .alert-success.alert"));
        Assert.assertTrue(element.isDisplayed(), "The element should be visible on the page.");

        driver.quit();
    }
}