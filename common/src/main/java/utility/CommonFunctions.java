package utility;

import resources.PropertiesManager;

public class CommonFunctions {

    private CommonFunctions() {
        //this class should not be instantiated as provides only static methods
    }

    public static String getEnvVariable(final String key, final PropertiesManager props) {
        return System.getProperty(key) != null ? System.getProperty(key) : props.getProperty(key);
    }

    public static String getEnvVariable(String key, String defaultValue) {
        return System.getProperty(key) != null ? System.getProperty(key) : defaultValue;
    }
}
