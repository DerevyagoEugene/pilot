package provider;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.getProperty;

public class TokenProvider {

    public static String getTokenByKey(String key) {
        Map<String, String> map = new HashMap<>()
        {
            { put("payment", getProperty("payment_token")); }
            { put("auth", getProperty("auth_token")); }
        };
        return map.get(key);
    }
}
