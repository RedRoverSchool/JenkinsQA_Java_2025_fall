package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class PipelineJobPage extends BasePage {

    public PipelineJobPage(WebDriver driver) {
        super(driver);
    }

    public PipelineJobPage clickDeletePipeline() {
        getDriver().findElement(By.className("confirmation-link"))
                .click();

        return this;
    }

    public HomePage confirmDeleteAtJobPage() {
        getDriver().findElement(By.cssSelector("[data-id='ok']"))
                .click();

        return new HomePage(getDriver());
    }

}
