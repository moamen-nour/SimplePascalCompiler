package com.mmz.simplepascalcompiler;
import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LexicalAnalyzer {
    private Pattern pattern;
    private TokensHandler tokensHandler;
    private ArrayList<Token> tokens;
    //The constructor has been made package private instead of public.
    //The constructor finds all matches with the main REGEX used from the source statement program.
    LexicalAnalyzer(){
        tokensHandler = new TokensHandler();
        tokens = new ArrayList<Token>();
        pattern = Pattern.compile("(?<token>PROGRAM|VAR|BEGIN|END\\.|END|FOR|READ|WRITE|TO|DO|;|:=|\\+|,|\\(|\\)|[a-zA-Z][\\w]*|\\*)");
    }
    //Matcher.Group function needs a minimum SDK of 26. Suppressed Warning for now.
    @SuppressLint("NewApi")
    //Tokenize method returns the array list of tokens analyzed from the source statements of the program.
    public ArrayList<Token> tokenize(String code){
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()){
            tokens.add(new Token(tokensHandler.getTspec(matcher.group("token")),tokensHandler.getTtype(matcher.group("token")) ));
        }
        return tokens;
    }
}
