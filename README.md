# TokenCrypt
A pluggable Java library for saving/retrieving sensitive "Tokens", built for storing OAuth2 tokens in an Android app

# Getting Started
The TokenCrypt class is where all interaction takes place. There are 3 methods: getToken, saveToken, and removeToken. You are responsible for implementing the TokenStore and KeyStore interfaces.

### TokenStore
TokenStore is the interface through which encrypted Token objects are saved/loaded, make sure this is a persistent store if you want tokens to persist between application launches.

### KeyStore
KeyStore is the interface through which the symmetric encryption key is saved/loaded from.

**Important** - Like TokenStore, the KeyStore implementation should be backed by a persistent store because once this key is lost or overwritten all data encrypted with it will be unreadable.

## Use
```
TokenStore tokenStore = *your implementation here*;
KeyStore keyStore = *your implementation here*;

TokenCrypt tokenCrypt = new TokenCrypt(tokenStore, keyStore);

// Create a Token
Token token = new Token("MyTokenId", "MyTokenValue");

// Save the token
tokenCrypt.saveToken(token);

// Retrieve/Delete the token
tokenCrypt.getToken("MyTokenId");
tokenCrypt.removeToken("MyTokenId");
```
### Customization
```
// Different levels of customization based on the TokenCrypt constructor you use

TokenCrypt tc = new TokenCrypt(tokenStore, keyStore);
TokenCrypt tc = new TokenCrypt(tokenStore, keyStore, algorithm); // Specify algorithm for key generation and encryption
TokenCrypt tc = new TokenCrypt(tokenStore, keyStore, tokenCryptor, keyAlgorithm); //
```
**TokenCryptor** - A default implementation is provided for which you can specify which encryption algorithm to use however if you would like to provide your own encrypt/decrypt methods plug it in here.
