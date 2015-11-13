package org.genio.tokencrypt;

public class TokenCryptorException extends Exception {

    protected Exception innerException;

    public TokenCryptorException(String message, Exception innerException) {
        super(message);
    }

    public TokenCryptorException(String message) {
        super(message);
    }

    public Exception getInnerException() {
        return innerException;
    }

    public void setInnerException(Exception innerException) {
        this.innerException = innerException;
    }

    @Override
    public String toString() {
        return String.format("Message: %s\nInner Exception: %s\nStack Trace: %s", this.getMessage(), this.innerException.getMessage(), this.innerException.getStackTrace());
    }
}
