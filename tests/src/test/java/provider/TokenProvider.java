package provider;

import java.util.HashMap;
import java.util.Map;

public class TokenProvider {

    public static String getTokenByKey(String key) {
        Map<String, String> map = new HashMap<>()
        {
            { put("payment","XXXXXXXXXXXX"); }
        };
        return map.get(key);
    }
}
