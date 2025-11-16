package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;

import java.util.List;

public class ApiPage extends BasePage {

    public ApiPage(WebDriver driver) { super(driver); }

    public String getHeadingText(){
       return getDriver().findElement(By.tagName("h1")).getText();
    }

    public List<String> getXmlJsonPythonApiLinks(){
        return getDriver()
                .findElements(By.xpath("//dt/a[@href]"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
