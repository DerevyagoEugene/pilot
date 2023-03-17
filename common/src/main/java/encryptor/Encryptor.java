package encryptor;

import org.testng.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    private Encryptor() {
    }

    public static String encryptValue(byte[] valueToEncrypt, EncryptionConfiguration encryptionConfiguration) {
        return encryptValue(valueToEncrypt,
                encryptionConfiguration.getKey(),
                encryptionConfiguration.getAlgorithm(),
                encryptionConfiguration.getCipherType(),
                encryptionConfiguration.getInitialVectorSize());
    }

    public static String decryptValue(byte[] valueToDecrypt, EncryptionConfiguration encryptionConfiguration) {
        return decryptValue(valueToDecrypt,
                encryptionConfiguration.getKey(),
                encryptionConfiguration.getAlgorithm(),
                encryptionConfiguration.getCipherType(),
                encryptionConfiguration.getInitialVectorSize());
    }

    private static byte[] getGeneratedBytes(byte[] valueToDecrypt, String key, String algorithm, String cipherType, int initVectorSize, int cipherMode) {
        byte[] generatedBytes = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(cipherType);
            cipher.init(cipherMode, secretKeySpec, new IvParameterSpec(new byte[initVectorSize]));
            generatedBytes = cipher.doFinal(valueToDecrypt);
        } catch (NoSuchAlgorithmException nsa) {
            Assert.fail(String.format("Unsupported cipher type: %s%nException: %s", cipherType, nsa.getMessage()));
        } catch (NoSuchPaddingException nsp) {
            Assert.fail(String.format("Unsupported padding%nException: %s", nsp.getMessage()));
        } catch (InvalidKeyException ik) {
            Assert.fail(String.format("Invalid key%nException: %s", ik.getMessage()));
        } catch (InvalidAlgorithmParameterException iap) {
            Assert.fail(String.format("Invalid algorithm parameter%nException: %s", iap.getMessage()));
        } catch (IllegalBlockSizeException ibs) {
            Assert.fail(String.format("Illegal block size%nException: %s", ibs.getMessage()));
        } catch (BadPaddingException bp) {
            Assert.fail(String.format("Bad padding%nException: %s", bp.getMessage()));
        }
        return generatedBytes;
    }

    private static String encryptValue(byte[] valueToEncrypt, String key, String algorithm, String cipherType, int initVectorSize) {
        byte[] encryptedBytes = getGeneratedBytes(valueToEncrypt, key, algorithm, cipherType, initVectorSize, Cipher.ENCRYPT_MODE);

        Assert.assertNotNull(encryptedBytes, "Can't perform value");
        return String.format("%x", new BigInteger(1, encryptedBytes));
    }

    private static String decryptValue(byte[] valueToDecrypt, String key, String algorithm, String cipherType, int initVectorSize) {
        return decryptValue(valueToDecrypt, key, algorithm, cipherType, initVectorSize, StandardCharsets.UTF_8);
    }

    private static String decryptValue(byte[] valueToDecrypt, String key, String algorithm, String cipherType, int initVectorSize, Charset encodingCharset) {
        byte[] decryptedBytes = getGeneratedBytes(valueToDecrypt, key, algorithm, cipherType, initVectorSize, Cipher.DECRYPT_MODE);

        Assert.assertNotNull(decryptedBytes, "Can't decrypt value");
        return new String(decryptedBytes, encodingCharset);
    }
}
