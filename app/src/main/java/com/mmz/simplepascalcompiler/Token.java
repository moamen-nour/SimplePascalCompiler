package com.mmz.simplepascalcompiler;

public class Token {
    private String tokenSpecifier;
    private int tokenType;
    private int line;


    public Token(String tokenSpecifier, int tokenType) {
        this.tokenSpecifier = tokenSpecifier;
        this.tokenType = tokenType;
    }

    public String getTokenSpecifier() {
        return tokenSpecifier;
    }

    public int getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "Tspecifier: " + tokenSpecifier + "\t\tTtype: " + tokenType ;
    }
}
