package school.redrover.testdata;

import org.openqa.selenium.WebDriver;
import school.redrover.page.*;

public enum ProjectPage {

    FREESTYLE_PROJECT_PAGE {
        @Override
        public BaseProjectPage createProjectPage(WebDriver driver) {
            return new FreestyleProjectPage(driver);
        }
    },

    PIPELINE_PROJECT_PAGE {
        @Override
        public BaseProjectPage createProjectPage(WebDriver driver) {
            return new PipelinePage(driver);
        }
    },

    MULTI_CONFIGURATION_PROJECT_PAGE {
        @Override
        public BaseProjectPage createProjectPage(WebDriver driver) {
            return new MultiConfigurationProjectPage(driver);
        }
    },

    FOLDER_PROJECT_PAGE {
        @Override
        public BaseProjectPage createProjectPage(WebDriver driver) {
            return new FolderPage(driver);
        }
    },

    MULTIBRANCH_PIPELINE_PROJECT_PAGE {
        @Override
        public BaseProjectPage createProjectPage(WebDriver driver) {
            return new MultibranchPipelineProjectPage(driver);
        }
    },

    ORGANIZATION_FOLDER_PROJECT_PAGE {
        @Override
        public BaseProjectPage createProjectPage(WebDriver driver) {
            return new OrganizationFolderPage(driver);
        }
    };

    public abstract BaseProjectPage createProjectPage(WebDriver driver);
}
