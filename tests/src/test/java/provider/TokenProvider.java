package provider;

import java.util.HashMap;
import java.util.Map;

public class TokenProvider {

    public static String getTokenByKey(String key) {
        Map<String, String> map = new HashMap<>()
        {
            { put("payment","Basic Njc3NGNlZmNmZjNkNDZlNmJkMGUzZDBiNTZmMWEwOTk6MmI3M0FiZmI3ODJGNEVERWE0RjhlMGM3NDkxNjMyODY="); }
            { put("2","Basic RHBlZ2MyOGc0NzlUWXhrSktUTnd4bWV6OmRYeEtaSkE5Y3RnY2Rna0ZzbUx1QzhaRA=="); }
            { put("3","Basic ZmJlMWE1MjBlM2Q1NGZjN2FjZGYyMWJhZTJlN2ZmYTk6NzBiY0Q4MzBCM0Q4NDFiYjgyMjU2NUM5N2MyMURGRGM=="); }
            { put("4","Basic MzI1NTIyMzczN2U5NDhjNzkzNGRmNGE4NmNiYzRlZGY6NUVEMUJiY2VEYjRmNEYwRjkwYjNhNTM3NTUyZjY4MWU="); }
            { put("5","Basic ZmE5YWU5MTA4Y2RjNDU4YTlkODQ5NzNmYmI4N2I5ZDg6M0QzQ2IxNDAyQ2E1NDM3MEIwM0YyRUVENDE0OTU3Y0E="); }
        };
        return map.get(key);
    }
}
