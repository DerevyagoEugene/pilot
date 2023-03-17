package encryptor;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import utility.Logger;

public class EncryptionFunctions {

    private EncryptionFunctions(){
        throw new IllegalStateException("Utility class");
    }

    private static final EncryptionConfiguration ENCRYPTION_CONFIGURATION = new EncryptionConfiguration();

    public static String encryptValue(String value) {
        return Encryptor.encryptValue(value.getBytes(), ENCRYPTION_CONFIGURATION);
    }

    public static String decryptValue(String value) {
        byte[] dehex = new byte[0];
        try {
            if (value != null) {
                dehex = Hex.decodeHex(value.toCharArray());
            }
        } catch (DecoderException e) {
            Logger.getInstance().fatal("Can't parse value as hex", new Exception());
        }
        return Encryptor.decryptValue(dehex, ENCRYPTION_CONFIGURATION);
    }
}
