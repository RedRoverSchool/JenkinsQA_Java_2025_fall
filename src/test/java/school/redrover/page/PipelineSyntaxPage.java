package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;

import java.util.List;

public class PipelineSyntaxPage extends BasePage {

    public PipelineSyntaxPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getListOfButtonsInSideMenu() {

        return getDriver()
                .findElements(By.className("task"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
