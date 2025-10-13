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

public class VitaliiZavTest {

    // Тесты на сайте automationexercise.com

    // Test Case 1: Register User
    @Test
    public void RegisterUser() {
        // 1. Launch browser
        WebDriver driver = new ChromeDriver();

        // Задержка на поиск элемента
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Navigate to url 'http://automationexercise.com'
        driver.get("http://automationexercise.com");

        // 3. Verify that home page is visible successfully
        // Считаем что страница полностью загружена если кнопка 'home' видна и окрашена в оранжевый цвет
        WebElement homeButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header .shop-menu li > a[href=\"/\"]"))
        );
        // У найденого элемента есть атрибут 'style' ?
        Assert.assertNull(homeButton.getAttribute("style"), "'homeButton' doesn't have attribute 'style'");
        // Элемент окрашен в оранжевый цвет ?
        Assert.assertTrue(homeButton.getAttribute("style").contains("color: orange;"), "Button isn`t orange");

        // 4. Click on 'Signup / Login' button
        // 5. Verify 'New User Signup!' is visible

        //=================  ТЕСТ НЕ ЗАКОНЧЕН ========================
    }
}
