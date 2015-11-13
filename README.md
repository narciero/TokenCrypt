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

// Create and save a Token
Token token = new Token("MyTokenId", "MyTokenValue");
tokenCrypt.saveToken(token);

// Retrieve/Delete the token
tokenCrypt.getToken("MyTokenId");
tokenCrypt.removeToken("MyTokenId");
```
### Customization
```
// Different levels of customization based on the TokenCrypt constructor you use

// Default - uses AES
TokenCrypt tc = new TokenCrypt(tokenStore, keyStore);

// Specify algorithm for key generation and encryption
TokenCrypt tc = new TokenCrypt(tokenStore, keyStore, algorithm);

// Specify TokenCryptor implementation to use for encryption/decryption along with key generation algorithm to use
TokenCrypt tc = new TokenCrypt(tokenStore, keyStore, tokenCryptor, keyAlgorithm);

// Specify TokenCryptor implementation to use for encryption/decryption along with a SecretKey object
TokenCrypt tc = new TokenCrypt(tokenStore, keyStore, tokenCryptor, secretKey);
```
**TokenCryptor** - A default implementation is provided for which you can specify the encryption algorithm to use (or default - AES) however if you would like to implement your own encrypt/decrypt methods plug it in here.
