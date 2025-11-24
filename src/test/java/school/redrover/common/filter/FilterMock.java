package school.redrover.common.filter;

import org.testng.*;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FilterMock {

    private static class TestClassImpl implements ITestClass {

        private final Class<?> realClass;

        public TestClassImpl(Class<?> realClass) {
            this.realClass = realClass;
        }

        @Override
        public ITestNGMethod[] getTestMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getBeforeTestMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getAfterTestMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getBeforeClassMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getAfterClassMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getBeforeSuiteMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getAfterSuiteMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getBeforeTestConfigurationMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getAfterTestConfigurationMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getBeforeGroupsMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public ITestNGMethod[] getAfterGroupsMethods() {
            return new ITestNGMethod[0];
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public XmlTest getXmlTest() {
            return null;
        }

        @Override
        public XmlClass getXmlClass() {
            return null;
        }

        @Override
        public String getTestName() {
            return "";
        }

        @Override
        public Class<?> getRealClass() {
            return realClass;
        }

        @Override
        public Object[] getInstances(boolean create) {
            return new Object[0];
        }

        @Override
        public long[] getInstanceHashCodes() {
            return new long[0];
        }

        @Override
        public void addInstance(Object instance) {

        }
    }

    private static class TestNGMethodImpl implements ITestNGMethod {

        private final TestClassImpl testClass;

        public TestNGMethodImpl(Class<?> testClass) {
            this.testClass = new TestClassImpl(testClass);
        }

        @Override
        public Class getRealClass() {
            return null;
        }

        @Override
        public ITestClass getTestClass() {
            return testClass;
        }

        @Override
        public void setTestClass(ITestClass iTestClass) {

        }

        @Override
        public String getMethodName() {
            return "";
        }

        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public long[] getInstanceHashCodes() {
            return new long[0];
        }

        @Override
        public String[] getGroups() {
            return new String[0];
        }

        @Override
        public String[] getGroupsDependedUpon() {
            return new String[0];
        }

        @Override
        public String getMissingGroup() {
            return "";
        }

        @Override
        public void setMissingGroup(String s) {

        }

        @Override
        public String[] getBeforeGroups() {
            return new String[0];
        }

        @Override
        public String[] getAfterGroups() {
            return new String[0];
        }

        @Override
        public String[] getMethodsDependedUpon() {
            return new String[0];
        }

        @Override
        public void addMethodDependedUpon(String s) {

        }

        @Override
        public boolean isTest() {
            return false;
        }

        @Override
        public boolean isBeforeMethodConfiguration() {
            return false;
        }

        @Override
        public boolean isAfterMethodConfiguration() {
            return false;
        }

        @Override
        public boolean isBeforeClassConfiguration() {
            return false;
        }

        @Override
        public boolean isAfterClassConfiguration() {
            return false;
        }

        @Override
        public boolean isBeforeSuiteConfiguration() {
            return false;
        }

        @Override
        public boolean isAfterSuiteConfiguration() {
            return false;
        }

        @Override
        public boolean isBeforeTestConfiguration() {
            return false;
        }

        @Override
        public boolean isAfterTestConfiguration() {
            return false;
        }

        @Override
        public boolean isBeforeGroupsConfiguration() {
            return false;
        }

        @Override
        public boolean isAfterGroupsConfiguration() {
            return false;
        }

        @Override
        public long getTimeOut() {
            return 0;
        }

        @Override
        public void setTimeOut(long l) {

        }

        @Override
        public int getInvocationCount() {
            return 0;
        }

        @Override
        public void setInvocationCount(int i) {

        }

        @Override
        public int getSuccessPercentage() {
            return 0;
        }

        @Override
        public String getId() {
            return "";
        }

        @Override
        public void setId(String s) {

        }

        @Override
        public long getDate() {
            return 0;
        }

        @Override
        public void setDate(long l) {

        }

        @Override
        public boolean canRunFromClass(IClass iClass) {
            return false;
        }

        @Override
        public boolean isAlwaysRun() {
            return false;
        }

        @Override
        public int getThreadPoolSize() {
            return 0;
        }

        @Override
        public void setThreadPoolSize(int i) {

        }

        @Override
        public boolean getEnabled() {
            return false;
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public void setDescription(String s) {

        }

        @Override
        public void incrementCurrentInvocationCount() {

        }

        @Override
        public int getCurrentInvocationCount() {
            return 0;
        }

        @Override
        public void setParameterInvocationCount(int i) {

        }

        @Override
        public int getParameterInvocationCount() {
            return 0;
        }

        @Override
        public void setMoreInvocationChecker(Callable<Boolean> callable) {

        }

        @Override
        public boolean hasMoreInvocation() {
            return false;
        }

        @Override
        public ITestNGMethod clone() {
            return null;
        }

        @Override
        public IRetryAnalyzer getRetryAnalyzer(ITestResult iTestResult) {
            return null;
        }

        @Override
        public void setRetryAnalyzerClass(Class<? extends IRetryAnalyzer> aClass) {

        }

        @Override
        public Class<? extends IRetryAnalyzer> getRetryAnalyzerClass() {
            return null;
        }

        @Override
        public boolean skipFailedInvocations() {
            return false;
        }

        @Override
        public void setSkipFailedInvocations(boolean b) {

        }

        @Override
        public long getInvocationTimeOut() {
            return 0;
        }

        @Override
        public boolean ignoreMissingDependencies() {
            return false;
        }

        @Override
        public void setIgnoreMissingDependencies(boolean b) {

        }

        @Override
        public List<Integer> getInvocationNumbers() {
            return List.of();
        }

        @Override
        public void setInvocationNumbers(List<Integer> list) {

        }

        @Override
        public void addFailedInvocationNumber(int i) {

        }

        @Override
        public List<Integer> getFailedInvocationNumbers() {
            return List.of();
        }

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        public void setPriority(int i) {

        }

        @Override
        public int getInterceptedPriority() {
            return 0;
        }

        @Override
        public void setInterceptedPriority(int i) {

        }

        @Override
        public XmlTest getXmlTest() {
            return null;
        }

        @Override
        public ConstructorOrMethod getConstructorOrMethod() {
            return null;
        }

        @Override
        public Map<String, String> findMethodParameters(XmlTest xmlTest) {
            return Map.of();
        }

        @Override
        public String getQualifiedName() {
            return "";
        }
    }

    public static class MethodInstanceImpl implements IMethodInstance {

        private final TestNGMethodImpl testNGMethod;

        public MethodInstanceImpl(Class<?> testClass) {
            this.testNGMethod = new TestNGMethodImpl(testClass);
        }

        @Override
        public ITestNGMethod getMethod() {
            return testNGMethod;
        }

        @Override
        public Object getInstance() {
            return null;
        }
    }
}
