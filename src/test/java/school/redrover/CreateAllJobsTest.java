package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;

public class CreateAllJobsTest extends BaseTest {

   public String generateRandomName() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

  public String getJobs(int value) {

        HashMap<Integer, String> projects = new HashMap<>();
        projects.put(0, "//span[text()='Freestyle project']");
        projects.put(1, "//span[text()='Pipeline']");
        projects.put(2, "//span[text()='Multi-configuration project']");
        projects.put(3, "//span[text()='Folder']");
        projects.put(4, "//span[text()='Multibranch Pipeline']");
        projects.put(5, "//span[text()='Organization Folder']");

        return projects.get(value);
    }

    @Test
    public void testCreateJobs() throws InterruptedException
    {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        int i;

        for (i = 0; i < 6; i++) {
            String name = generateRandomName();
            getDriver().findElement(By.id("name")).sendKeys(name);
            getDriver().findElement(By.xpath(getJobs(i))).click();
            getDriver().findElement(By.id("ok-button")).click();

            Thread.sleep(2000);
            getDriver().findElement(By.xpath("//span[text()='Jenkins']")).click();
            getDriver().findElement(By.xpath("//span[text()='" + name + "']"));

            getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        }

        Assert.assertEquals(i, 6);
    }
}
