package com.mmz.simplepascalcompiler;
public class Token {
    //Attributes held by Token Object : TokenSpecifier - TokenType.
    private String tokenSpecifier;
    private int tokenType;
    //Constructor changed to private apckage instead of public.
    //Sets tokenSpecifier and token type for a token.
    Token(String tokenSpecifier, int tokenType) {
        this.tokenSpecifier = tokenSpecifier;
        this.tokenType = tokenType;
    }
    //Method 1: returns token specifier of this specific token object.
    public String getTokenSpecifier() {
        return tokenSpecifier;
    }
    //Method 2: returns token type of this specific token object.
    public int getTokenType() {
        return tokenType;
    }
    //Overridden the Object method for debugging and showing purposes.
    @Override
    public String toString() {
        return "Tspecifier: " + tokenSpecifier + "\t\tTtype: " + tokenType ;
    }
}
