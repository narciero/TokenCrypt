package org.genio.tokencrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;

public class TokenCryptorImpl implements TokenCryptor {

    private String algorithm;

    public TokenCryptorImpl(String algorithm) {
        this.algorithm = algorithm;
    }

    public String encrypt(String input, SecretKey key) throws TokenCryptorException {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = cipher.doFinal(input.getBytes(Charset.defaultCharset()));
            return new String(encVal, Charset.defaultCharset());
        }
        catch (Exception e) {
            throw new TokenCryptorException("encrypt error", e);
        }
    }

    public String decrypt(String input, SecretKey key) throws TokenCryptorException {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decVal = cipher.doFinal(input.getBytes(Charset.defaultCharset()));
            return new String(decVal, Charset.defaultCharset());
        }
        catch (Exception e) {
            throw new TokenCryptorException("decrypt error", e);
        }
    }
}
