package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class PipelineHistoryConsolePage extends BasePage {

    public PipelineHistoryConsolePage(WebDriver driver) {
        super(driver);
    }

    public String getConsoleOutput() {
        return getDriver().findElement(By.id("out")).getText();
    }
}
