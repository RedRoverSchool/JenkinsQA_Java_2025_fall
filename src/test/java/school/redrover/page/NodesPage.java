package school.redrover.page;

import com.google.common.collect.RangeMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.ranges.Range;
import school.redrover.common.BasePage;

import java.util.stream.IntStream;

public class NodesPage  extends BasePage {

    public NodesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href ='new']")
    WebElement newNode;

    @FindBy(name = "nodeDescription")
    WebElement description;

    @FindBy(name = "Submit")
    WebElement submitForm;

    @FindBy(name = "_.numExecutors")
    WebElement numberExecutors;

    @FindBy(name = "_.labelString")
    WebElement labels;

    @FindBy(name = "mode")
    WebElement selectUsage;

    @FindBy(xpath = "(//div[@class ='jenkins-select'])[2]/select")
    WebElement selectLaunchMethod;

    @FindBy(xpath = "(//select[@class='jenkins-select__input dropdownList'])[3]")
    WebElement selectAvailability;

    public NewNodePage goToNewNodePage() {
        newNode.click();

        return new NewNodePage(getDriver());
    }

    public NodesPage addDescription(String text) {
        description.clear();
        description.sendKeys(text);

        return this;
    }

    public NodesPage submitNodeForm() {
        submitForm.click();

        return this;
    }

    public NodesPage enterNumberExecutors(int num) {
        numberExecutors.clear();
        numberExecutors.sendKeys(Integer.toString(num));

        return  this;
    }

    public NodesPage addLabels(String[] labelArray) {
        labels.clear();
        labels.sendKeys(labelArray);

        return this;
    }

    public NodesPage selectUsageOption(String item) {
        Select select = new Select(selectUsage);
        select.selectByValue(item);

        return this;
    }

    public NodesPage selectLaunchOption(String value) {
        Select select = new Select(selectLaunchMethod);
        select.selectByValue(value);

        return this;
    }

    public NodesPage selectAvailabilityOption(String text) {
        Select select = new Select(selectAvailability);
        select.selectByVisibleText(text);

        return this;
    }

    public NodesPage checkNodeProperties( int number ) {
            getDriver().findElement(By.cssSelector("input[id='cb" + number +  "'] + label"));

            return this;
    }

    public String findNodesInList(String name) {
       WebElement  item = getDriver().findElement(By.xpath("//a[@href='../computer/" + name + "/']"));

       return item.getText();
    }
}

