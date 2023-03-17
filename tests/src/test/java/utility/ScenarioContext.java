package utility;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/***
 * ScenarioContext allows you to share state between steps in a scenario.
 * It guarantees the clear state in a new scenario.
 */
public class ScenarioContext {

    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    public static Map<String, Object> getInstance() {
        if (isNull(context.get())) {
            context.set(new HashMap<>());
        }
        return context.get();
    }
}
