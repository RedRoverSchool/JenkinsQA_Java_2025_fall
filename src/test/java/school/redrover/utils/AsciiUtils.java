package school.redrover.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class AsciiUtils {
    public static CharSequence getRandomAsciiCharUtil() {
    final char[] RANGE_ASCII =
            java.util.stream.Stream.of(
                            IntStream.rangeClosed(33, 255)
                    ).flatMapToInt(s -> s)
                    .collect(StringBuilder::new,
                            (sb, cp) -> sb.append((char) cp),
                            StringBuilder::append)
                    .toString()
                    .toCharArray();

    int i = ThreadLocalRandom.current().nextInt(RANGE_ASCII.length);
    return String.valueOf(RANGE_ASCII[i]);
}

public static CharSequence getRandomInvalidAsciiCharForNameFieldsValidationUtil() {
        final char[] RANGE_NAME =
                java.util.stream.Stream.of(
                                IntStream.rangeClosed(33, 44), //  except control chars [0;31], except space char [32],except dash [45]
                                IntStream.rangeClosed(46, 47), //  except digits 0-9 = [48;57]
                                IntStream.rangeClosed(58, 64), //  except letters A-Z = [65;90]
                                IntStream.rangeClosed(91, 94), // except underscore = 95
                                IntStream.of(96), //  except letters a-z = [97;122]
                                IntStream.rangeClosed(123, 126), //except control char [127]
                                IntStream.rangeClosed(128, 255)
                        ).flatMapToInt(s -> s)
                        .collect(StringBuilder::new,
                                (sb, cp) -> sb.append((char) cp),
                                StringBuilder::append)
                        .toString()
                        .toCharArray();

        int i = ThreadLocalRandom.current().nextInt(RANGE_NAME.length);
        return String.valueOf(RANGE_NAME[i]);
    }

    public static CharSequence getRandomInvalidAsciiCharForEmailUtil() {
        final char[] RANGE_EMAIL =
                java.util.stream.Stream.of(
                                IntStream.rangeClosed(32, 63), //except [64] = @
                                IntStream.rangeClosed(65, 255)
                        ).flatMapToInt(s -> s)
                        .collect(StringBuilder::new,
                                (sb, cp) -> sb.append((char) cp),
                                StringBuilder::append)
                        .toString()
                        .toCharArray();

        int i = ThreadLocalRandom.current().nextInt(RANGE_EMAIL.length);
        return String.valueOf(RANGE_EMAIL[i]);
    }
}
