package utility;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.*;

@UtilityClass
public class SortingUtilities {

    @SneakyThrows
    public static List<String> sort(Collection<String> collection, boolean isAscendingOrder) {
        List<String> expectedOrder = new ArrayList<>(collection);
        String rules = ((RuleBasedCollator) Collator.getInstance(Locale.US)).getRules();
        RuleBasedCollator comparator = new RuleBasedCollator(rules);
        comparator.setStrength(Collator.SECONDARY);
        expectedOrder.sort(isAscendingOrder ? comparator : comparator.reversed());
        return expectedOrder;
    }
}
