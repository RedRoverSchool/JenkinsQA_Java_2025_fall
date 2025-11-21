package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class PipelineHistoryPage extends BasePage {

    public PipelineHistoryPage(WebDriver driver) { super(driver); }

    public PipelineHistoryConsolePage clickConsoleOutput() {
        getDriver().findElement(By.xpath("//a[substring-before(@href, 'console')]"))
                .click();

        return new PipelineHistoryConsolePage(getDriver());
    }
}
