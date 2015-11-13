package org.genio.tokencrypt;

/**
 * Created by narciero on 11/12/15.
 */
public class Token {

    private String id;
    private String value;

    public Token(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
