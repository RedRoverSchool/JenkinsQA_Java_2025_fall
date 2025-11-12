package school.redrover.common;

import org.testng.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterForTests implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        String changed = System.getenv("CHANGED_TEST_FILES");
        String deleted = System.getenv("DELETED_TEST_FILES");

        if (changed != null || deleted != null) {
            Set<String> changedFiles = new HashSet<>();
            Set<String> deletedFiles = new HashSet<>();

            for (String f : changed.split(";")) {
                String path = f.trim();
                changedFiles.add(path);
            }

            for (String f : deleted.split(";")) {
                String path = f.trim();
                deletedFiles.add(path);
            }

            boolean hasNonTest = Stream.concat(deletedFiles.stream(), changedFiles.stream())
                    .anyMatch(f -> !f.endsWith("Test.java"));

            if (hasNonTest) {
                return methods;
            }

            Map<Class<?>, String> classMap = methods.stream()
                    .map(IMethodInstance::getMethod).map(ITestNGMethod::getTestClass).map(IClass::getRealClass)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            clazz -> String.format("src/test/java/%s.java", clazz.getName().replace('.', '/')),
                            (pathA, pathB) -> pathA
                    ));

            return methods.stream()
                    .filter(method -> changedFiles.contains(classMap.get(method.getMethod().getTestClass().getRealClass())))
                    .collect(Collectors.toList());
        }

        return methods;
    }
}
