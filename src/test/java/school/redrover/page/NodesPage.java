package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class NodesPage  extends BasePage {

    public NodesPage(WebDriver driver) {
        super(driver);
    }

    public RestApiPage clickRestApiLink(){
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        return new RestApiPage(getDriver());
    }
}

