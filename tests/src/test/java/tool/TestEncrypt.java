package tool;

import org.testng.annotations.Test;

import static encryptor.EncryptionFunctions.decryptValue;
import static encryptor.EncryptionFunctions.encryptValue;
import static org.testng.Assert.assertEquals;

public class TestEncrypt {

    @Test(enabled = false)
    public void testEncrypt() {
        String str = "TEST";
        String encryptedStr = encryptValue(str);
        String decryptedStr = decryptValue(encryptedStr);
        System.out.println("Encrypted: " + encryptedStr);
        assertEquals(str, decryptedStr, "Original and decrypted strings should be equal");
    }
}
