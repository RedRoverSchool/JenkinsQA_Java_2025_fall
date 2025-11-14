package school.redrover.utils;

import java.util.concurrent.ThreadLocalRandom;

public class GetRandomUtils {
    public static Integer getRandomInteger() {
        int min = 0;
        int max = Integer.MAX_VALUE;
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
