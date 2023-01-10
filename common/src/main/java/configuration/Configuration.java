package configuration;

import lombok.experimental.UtilityClass;
import resources.PropertiesManager;

@UtilityClass
public class Configuration {

    private final PropertiesManager manager = new PropertiesManager("config.properties");

    public static String getApiUrl() {
        return manager.getProperty("api.url");
    }

    public static String getXrayUrl() {
        return manager.getProperty("xray.url");
    }

    public static String getJiraUrl() {
        return manager.getProperty("jira.url");
    }
}
