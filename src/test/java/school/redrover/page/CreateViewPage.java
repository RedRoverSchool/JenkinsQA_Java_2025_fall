package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class CreateViewPage extends BasePage {

    public CreateViewPage(WebDriver driver) {
        super(driver);
    }

    public CreateViewPage sendViewName(String name){
        getDriver().findElement(By.id("name")).sendKeys(name);

        return this;
    }

    public CreateViewPage clickMyViewName(){
        getDriver().findElement(By.xpath("//label[text() = 'My View']")).click();

        return this;
    }

    public HomePage clickCreateButtonForNewView() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok"))).click();

        return new HomePage(getDriver());
    }
}
