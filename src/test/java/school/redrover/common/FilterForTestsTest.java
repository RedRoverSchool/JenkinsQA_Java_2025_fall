package school.redrover.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FilterForTestsTest {

    private static final String TEST_A = "src/test/java/ExampleATest.java";
    private static final String TEST_B = "src/test/java/ExampleBTest.java";

    private static final String BASE_PAGE = "src/test/java/BasePage.java";
    private static final String PAGE_A = "src/test/java/ExampleAPage.java";
    private static final String PAGE_B = "src/test/java/ExampleBPage.java";

    private static final String DATA_FILE = "src/test/resources/data.json";

    private final FilterForTests filter = new FilterForTests();

    private Set<String> collect(Set<String> changedFiles, Map<String, Set<String>> graph) {
        Set<String> affectedFiles = new HashSet<>();
        Set<String> visitedFiles = new HashSet<>();

        try {
            Method collectLeavesMethod = FilterForTests.class.getDeclaredMethod(
                    "collectLeaves", String.class, Map.class, Set.class, Set.class
            );
            collectLeavesMethod.setAccessible(true);

            for (String file : changedFiles) {
                collectLeavesMethod.invoke(filter, file, graph, affectedFiles, visitedFiles);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return affectedFiles;
    }

    @Test
    public void testChainOfMultiplePages() {
        Set<String> changedFiles = Set.of(BASE_PAGE);
        Map<String, Set<String>> graph = Map.of(
                BASE_PAGE, Set.of(PAGE_A),
                PAGE_A, Set.of(PAGE_B),
                PAGE_B, Set.of(TEST_A)
        );
        Set<String> affectedFiles = collect(changedFiles, graph);

        Assert.assertEquals(affectedFiles, Set.of(TEST_A));
    }

    @Test
    public void testSinglePageDependency() {
        Set<String> changedFiles = Set.of(PAGE_A);
        Map<String, Set<String>> graph = Map.of(
                PAGE_A, Set.of(TEST_A)
        );
        Set<String> affectedFiles = collect(changedFiles, graph);

        Assert.assertEquals(affectedFiles, Set.of(TEST_A));
    }

    @Test
    public void testChangedTestDirectly() {
        Set<String> changedFiles = Set.of(TEST_B);
        Map<String, Set<String>> graph = Map.of();
        Set<String> affectedFiles = collect(changedFiles, graph);

        Assert.assertEquals(affectedFiles, Set.of(TEST_B));
    }

    @Test
    public void testChangedNonTestNonPageFile() {
        Set<String> changedFiles = Set.of(DATA_FILE);
        Map<String, Set<String>> graph = Map.of();
        Set<String> affectedFiles = collect(changedFiles, graph);

        Assert.assertEquals(affectedFiles, Set.of(DATA_FILE));
    }
}
