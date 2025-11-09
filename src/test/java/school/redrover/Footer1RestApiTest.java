package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

    public class Footer1RestApiTest extends BaseTest {

        private String getRestApiHeader(){
            getWait2().
                    until(ExpectedConditions.presenceOfElementLocated(By.className("rest-api"))).click();
            return getDriver().findElement(By.cssSelector("h1")).getText();
        }

        @Test
        public void testRestApiMainPage(){
            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }

        @Test
        public void  testRestApiUserPage() {
            getDriver().findElement(By.id("root-action-UserAction")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }

        @Test
        public void testRestApiNewItemPage(){
            getWait2().
                   until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/div[1]/div[1]/span/a"))).
                    click();


            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }
        @Test
        public void testRestApiNewNodesPage() {
            getDriver().findElement(By.xpath("//div/section[2]/ul/li[1]/a")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }
        @Test
        public void testRestApiNodesPage(){

            getDriver().findElement(By.linkText("Build Executor Status")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }
        @Test
        public void testRestApiBuildHistoryOfJenkinsPage(){
            getDriver().findElement(By.xpath("//div[2]/span/a")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }
    }


