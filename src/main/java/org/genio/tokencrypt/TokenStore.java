package org.genio.tokencrypt;

public interface TokenStore {

    Token getToken(String tokenId);

    void saveToken(Token token);

    void removeToken(String tokenId);
}
