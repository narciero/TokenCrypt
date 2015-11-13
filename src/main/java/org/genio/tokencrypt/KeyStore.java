package org.genio.tokencrypt;

import javax.crypto.SecretKey;

public interface KeyStore {

    SecretKey getKey(String keyId);

    void saveKey(String keyId, SecretKey key);
}
