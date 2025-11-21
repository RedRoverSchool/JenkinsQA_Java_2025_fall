package school.redrover.common.filter;

import org.testng.*;
import org.testng.annotations.Test;

import java.util.List;

public class FilterForTestsTest {

    private static class ExampleTest {}
    private static class LoginTest {}
    private static class DashboardTest {}

    @Test
    public void testDeletedClass() {
        List<String> fileList = List.of("D=src/test/java/FileTest.java");
        String dependenciesClasses = "";
        List<IMethodInstance> methodList = List.of(new FilterMock.MethodInstanceImpl(FilterForTestsTest.class));

        List<IMethodInstance> resultList = FilterUtils.filterMethods(fileList, dependenciesClasses, methodList);

        Assert.assertEquals(resultList.size(), 0);
    }

    @Test
    public void testNoChangesClass() {
        List<String> fileList = List.of();
        String dependencies = "";
        List<IMethodInstance> methodList = List.of(new FilterMock.MethodInstanceImpl(FilterForTestsTest.class));

        List<IMethodInstance> resultList = FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 0);
    }

    @Test
    public void testAddNewTest() {
        List<String> fileList = List.of("A=src/test/java/ExampleTest.java");
        String dependencies = "";
        List<IMethodInstance> methodList = List.of(
                new FilterMock.MethodInstanceImpl(ExampleTest.class)
        );

        List<IMethodInstance> resultList = FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 1);
        Assert.assertEquals(resultList.get(0).getMethod().getTestClass().getRealClass(), ExampleTest.class);
    }

    @Test
    public void tesRenameTest() {
        List<String> fileList = List.of("R=src/test/java/ExampleTest.java");
        String dependencies = "";
        List<IMethodInstance> methodList = List.of(
                new FilterMock.MethodInstanceImpl(ExampleTest.class)
        );

        List<IMethodInstance> resultList =
                FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 1);
        Assert.assertEquals(resultList.get(0).getMethod().getTestClass().getRealClass(), ExampleTest.class);
    }

    @Test
    public void testModifiedTest() {
        List<String> fileList = List.of("M=src/test/java/ExampleTest.java");
        String dependencies = "";
        List<IMethodInstance> methodList = List.of(
                new FilterMock.MethodInstanceImpl(ExampleTest.class)
        );

        List<IMethodInstance> resultList =
                FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 1);
        Assert.assertEquals(resultList.get(0).getMethod().getTestClass().getRealClass(), ExampleTest.class);
    }

    @Test
    public void testClassWithDependencies() {
        List<String> fileList = List.of("M=src/main/java/BasePage.java");
        String dependencies = "src/main/java/BasePage.java=src/main/java/Page.java;" +
                "src/main/java/Page.java=src/test/java/LoginTest.java;" +
                "src/main/java/Page.java=src/test/java/DashboardTest.java";

        List<IMethodInstance> methodList = List.of(
                new FilterMock.MethodInstanceImpl(LoginTest.class),
                new FilterMock.MethodInstanceImpl(DashboardTest.class)
        );

        List<IMethodInstance> resultList =
                FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 2);
        Assert.assertTrue(resultList.stream().anyMatch(x -> x.getMethod().getTestClass().getRealClass() == LoginTest.class));
        Assert.assertTrue(resultList.stream().anyMatch(x -> x.getMethod().getTestClass().getRealClass() == DashboardTest.class));
    }

    @Test
    public void testClassTopLevelNoDependencies() {
        List<String> fileList = List.of("M=src/main/java/Utils.java");
        String dependencies = "";
        List<IMethodInstance> methodList = List.of(
                new FilterMock.MethodInstanceImpl(ExampleTest.class),
                new FilterMock.MethodInstanceImpl(LoginTest.class)
        );

        List<IMethodInstance> resultList =
                FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 2);
    }

    @Test
    public void testOneDependency() {
        List<String> fileList = List.of("M=src/main/java/Service.java");
        String dependencies =
                "src/main/java/BasePage.java=src/main/java/LoginTest.java;";

        List<IMethodInstance> methodList = List.of(new FilterMock.MethodInstanceImpl(LoginTest.class));

        List<IMethodInstance> resultList =
                FilterUtils.filterMethods(fileList, dependencies, methodList);

        Assert.assertEquals(resultList.size(), 1);
        Assert.assertTrue(resultList.stream().anyMatch(x -> x.getMethod().getTestClass().getRealClass() == LoginTest.class));
    }

}
