package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItem1Test extends BaseTest {

    @Test
    public void testItemsAreDisplayed() {

        String[] expectedItemsList = {
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        };

        String[] actualItemsList = new String[6];

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        for (int i = 0; i < expectedItemsList.length; i++) {
            actualItemsList[i] = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(expectedItemsList[i]))).getText();
        }

        Assert.assertEquals(actualItemsList,expectedItemsList);

    }
}
