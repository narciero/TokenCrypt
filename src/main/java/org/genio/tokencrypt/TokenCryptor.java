package org.genio.tokencrypt;

import javax.crypto.SecretKey;

public interface TokenCryptor {

    String encrypt(String input, SecretKey key) throws TokenCryptorException;

    String decrypt(String input, SecretKey key) throws TokenCryptorException;
}
