package com.mmz.simplepascalcompiler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private Matcher matcher;
    private Pattern pattern;
    private TokensHandler tokensHandler;

    private ArrayList<Token> tokens;

    public LexicalAnalyzer (){
        tokensHandler = new TokensHandler();
        tokens = new ArrayList<Token>();
        pattern = Pattern.compile("(?<token>PROGRAM|VAR|BEGIN|END\\.|END|FOR|READ|WRITE|TO|DO|;|:=|\\+|,|\\(|\\)|[a-zA-Z][\\w]*|\\*)");
    }

    public ArrayList<Token> tokenize(String code){

        matcher = pattern.matcher(code);
        while (matcher.find()){
            tokens.add(new Token(tokensHandler.getTspec(matcher.group("token")),tokensHandler.getTtype(matcher.group("token")) ));

//        System.out.println(matcher.group("token"));
        }


//       for (Token t:tokens) {
//           System.out.println(t.toString());
//       }

        return tokens;

    }

}
