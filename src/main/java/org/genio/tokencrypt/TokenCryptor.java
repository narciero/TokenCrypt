package org.genio.tokencrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

public class TokenCryptor {

    private static final String DEFAULT_ALGORITHM = "AES";

    protected String algorithm;
    protected KeyStore keyStore;
    protected TokenStore tokenStore;

    public TokenCryptor(TokenStore tokenStore, KeyStore keyStore) throws NoSuchAlgorithmException {
        this(tokenStore, keyStore, DEFAULT_ALGORITHM);
    }

    public TokenCryptor(TokenStore tokenStore, KeyStore keyStore, String algorithm) throws NoSuchAlgorithmException {
        this.keyStore = keyStore;
        this.tokenStore = tokenStore;
        this.algorithm = algorithm;

        SecretKey key = SecretKeyGenerator.generateKey(this.algorithm);
        this.keyStore.saveKey(algorithm, key);
    }

    public Token getToken(String tokenId) throws TokenCryptorException {
        Token encryptedToken = this.tokenStore.getToken(tokenId);
        String token = this.decrypt(encryptedToken.getValue());
        return new Token(tokenId, token);
    }

    public void saveToken(Token token) throws TokenCryptorException {
        String encryptedTokenValue = this.encrypt(token.getValue());
        Token encryptedToken = new Token(token.getId(), encryptedTokenValue);
        this.tokenStore.saveToken(encryptedToken);
    }

    public void removeToken(String tokenId) {
        this.tokenStore.removeToken(tokenId);
    }

    private String encrypt(String input) throws TokenCryptorException {
        SecretKey key = this.keyStore.getKey(this.algorithm);
        if (key != null) {
            try {
                Cipher cipher = Cipher.getInstance(this.algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] encVal = cipher.doFinal(input.getBytes(Charset.defaultCharset()));
                return new String(encVal, Charset.defaultCharset());
            }
            catch (Exception e) {
                throw new TokenCryptorException(e.getMessage());
            }
        }
        throw new NullPointerException("Failed to retrieve key from the KeyStore");
    }

    private String decrypt(String input) throws TokenCryptorException {
        SecretKey key = this.keyStore.getKey(this.algorithm);
        if (key != null) {
            try {
                Cipher cipher = Cipher.getInstance(this.algorithm);
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] decVal = cipher.doFinal(input.getBytes(Charset.defaultCharset()));
                return new String(decVal, Charset.defaultCharset());
            }
            catch (Exception e) {
                throw new TokenCryptorException(e.getMessage());
            }
        }

        throw new NullPointerException("Failed to retrieve key from the KeyStore");
    }
}
