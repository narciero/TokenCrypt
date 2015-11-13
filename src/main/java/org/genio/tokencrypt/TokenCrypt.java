package org.genio.tokencrypt;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class TokenCrypt {

    private static final String DEFAULT_ALGORITHM = "AES";
    private static final String KEY_ID = "TOKENCRYPT_SKEY";

    protected KeyStore keyStore;
    protected TokenStore tokenStore;
    protected TokenCryptor tokenCryptor;

    public TokenCrypt(TokenStore tokenStore, KeyStore keyStore) throws NoSuchAlgorithmException {
        this(tokenStore, keyStore, DEFAULT_ALGORITHM);
    }

    public TokenCrypt(TokenStore tokenStore, KeyStore keyStore, String algorithm) throws NoSuchAlgorithmException {
        this(tokenStore, keyStore, new TokenCryptorImpl(algorithm), algorithm);
    }

    public TokenCrypt(TokenStore tokenStore, KeyStore keyStore, TokenCryptor tokenCryptor, String keyAlgorithm) throws NoSuchAlgorithmException {
        this(tokenStore, keyStore, tokenCryptor, SecretKeyGenerator.generateKey(keyAlgorithm));
    }

    public TokenCrypt(TokenStore tokenStore, KeyStore keyStore, TokenCryptor tokenCryptor, SecretKey symmetricKey) throws NoSuchAlgorithmException {
        this.tokenStore = tokenStore;
        this.keyStore = keyStore;
        this.tokenCryptor = tokenCryptor;

        if (!keyStore.containsKey(KEY_ID)) {
            keyStore.saveKey(KEY_ID, symmetricKey);
        }
    }

    public Token getToken(String tokenId) throws TokenCryptorException {
        SecretKey key = this.keyStore.getKey(KEY_ID);
        Token encryptedToken = this.tokenStore.getToken(tokenId);
        String token = this.tokenCryptor.decrypt(encryptedToken.getValue(), key);
        return new Token(tokenId, token);
    }

    public void saveToken(Token token) throws TokenCryptorException {
        SecretKey key = this.keyStore.getKey(KEY_ID);
        String encryptedTokenValue = this.tokenCryptor.encrypt(token.getValue(), key);
        Token encryptedToken = new Token(token.getId(), encryptedTokenValue);
        this.tokenStore.saveToken(encryptedToken);
    }

    public void removeToken(String tokenId) {
        this.tokenStore.removeToken(tokenId);
    }

    // Clears all token/key stores
    public void reset() {
        this.tokenStore.removeAllToken();
        this.keyStore.removeAllKeys();
    }
}
