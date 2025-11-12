package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineProjectPage extends BasePage {

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='view-message']/../h1")))
                .getText();
    }
}
