package encryptor;

import org.testng.Assert;
import resources.PropertiesManager;

import static java.lang.System.getProperty;
import static utility.CommonFunctions.getEnvVariable;

public class EncryptionConfiguration {

    private static final String ENCRYPTION_PROPS_FILE_NAME = "encryption.properties";

    private static final String ENCRYPTION_KEY_PARAMETER_NAME = "encryption.key";
    private static final String ENCRYPTION_ALGORITHM_PARAMETER_NAME = "encryption.algorithm";
    private static final String ENCRYPTION_CIPHER_TYPE_PARAMETER_NAME = "encryption.cipherType";
    private static final String ENCRYPTION_INIT_VECTOR_SIZE_PARAMETER_NAME = "encryption.initVectorSize";

    private PropertiesManager encryptionProps;

    public EncryptionConfiguration() {
        //Constructor does not takes any arguments because class only provides values from configuration file
    }

    public String getKey() {
        String key = getEnvVariable(ENCRYPTION_KEY_PARAMETER_NAME, getEncryptionProps());
        return key.equals(ENCRYPTION_KEY_PARAMETER_NAME) ? getProperty("encryption_key") : key;
    }

    public String getAlgorithm() {
        return getEnvVariable(ENCRYPTION_ALGORITHM_PARAMETER_NAME, getEncryptionProps());
    }

    public String getCipherType() {
        return getEnvVariable(ENCRYPTION_CIPHER_TYPE_PARAMETER_NAME, getEncryptionProps());
    }

    public Integer getInitialVectorSize() {
        String initialVectorSize = getEnvVariable(ENCRYPTION_INIT_VECTOR_SIZE_PARAMETER_NAME, getEncryptionProps());
        if (initialVectorSize != null) {
            return Integer.parseInt(initialVectorSize);
        } else {
            Assert.fail(String.format("Can't find value for key '%s'", ENCRYPTION_INIT_VECTOR_SIZE_PARAMETER_NAME));
            return null;
        }
    }

    private PropertiesManager getEncryptionProps() {
        if (encryptionProps == null) {
            encryptionProps = new PropertiesManager(ENCRYPTION_PROPS_FILE_NAME);
        }
        return encryptionProps;
    }
}
