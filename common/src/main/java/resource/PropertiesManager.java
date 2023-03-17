package resource;

import utility.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public final class PropertiesManager {

    private final Properties properties = new Properties();

    private static final Logger logger = Logger.getInstance();

    public PropertiesManager(final String resourceName) {
        appendFromResource(properties, resourceName);
    }

    private void appendFromResource(final Properties objProperties, final String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (nonNull(inStream)) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException(format("Resource \"%1$s\" could not be found", resourceName));
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(final String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

}
