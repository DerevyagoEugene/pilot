package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomFunctions {

    private static final String COND_MESSAGE_ANY = "Any";
    private static final char[] UNALLOWABLE_SPECIAL_SYMBOLS = {'&', '>', '<', '#', '%', '^', '&', '*', '~', '%'};

    private RandomFunctions() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer getRandomInt() {
        return ThreadLocalRandom
                .current()
                .nextInt();
    }

    public static int getRandomInt(Integer rightBound) {
        return ThreadLocalRandom
                .current()
                .nextInt(rightBound);
    }

    public static Integer getRandomInt(Integer leftBound, Integer rightBound) {
        return ThreadLocalRandom
                .current()
                .nextInt(leftBound, rightBound);
    }

    public static <T> T getRandomElementFromList(List<T> objects) {
        return getRandomElementFromList(objects, item -> true, COND_MESSAGE_ANY);
    }

    public static <T> T getRandomElementBut(List<T> list, Predicate<T> condition, String message) {
        return list
                .stream()
                .filter(condition)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(message));
    }

    public static <T> T getRandomElementFromList(List<T> objects, Predicate<T> condition, String conditionMessage) {
        return getNRandomElementsFromList(objects, 1, condition, conditionMessage).get(0);
    }

    public static <T> List<T> getNRandomElementsFromList(List<T> objects,
                                                         int quantity) {
        return getNRandomElementsFromList(objects, quantity, item -> true, COND_MESSAGE_ANY);
    }

    public static String generateSpecialCharsString(int length) {
        return IntStream.range(0, length)
                .mapToObj(index -> String.valueOf(UNALLOWABLE_SPECIAL_SYMBOLS[getRandomInt(UNALLOWABLE_SPECIAL_SYMBOLS.length)]))
                .collect(Collectors.joining());
    }

    private static <T> List<T> getNRandomElementsFromList(List<T> objects, int quantity, Predicate<T> condition, String conditionMessage) {
        if (objects.isEmpty()) {
            throw new IllegalArgumentException("Initial list of objects is empty. Can't get random");
        }
        List<T> copyObjects = new ArrayList<>(objects);
        Collections.shuffle(copyObjects);
        List<T> results = copyObjects
                .stream()
                .filter(condition)
                .limit(quantity)
                .collect(Collectors.toList());

        if (results.size() < quantity) {
            throw new IllegalStateException(String.format("%1$s '%2$s' elements can't be found in the list of '%3$s' size'", conditionMessage, quantity, copyObjects.size()));
        }
        return results;
    }
}
