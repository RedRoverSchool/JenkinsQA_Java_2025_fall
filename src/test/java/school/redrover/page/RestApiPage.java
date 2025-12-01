package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;

public class RestApiPage extends BasePage {

    public RestApiPage(WebDriver driver) { super(driver); }

    public String getHeadingText(){

        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public List<String> getXmlJsonPythonApiLinksText(){
        return getDriver()
                .findElements(By.xpath("//dt/a[@href]"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getNamePage(){
        return getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("h1"))).getText();
    }
}
