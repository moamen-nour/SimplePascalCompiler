package com.mmz.simplepascalcompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TokensHandler {
    private HashMap <String , Integer> tokenSpecMap;

    public TokensHandler(){
        tokenSpecMap = new HashMap<>();
        tokenSpecMap.put("PROGRAM" ,1);
        tokenSpecMap.put("VAR" ,2);
        tokenSpecMap.put("BEGIN" ,3);
        tokenSpecMap.put("END" ,4);
        tokenSpecMap.put("END.",5);
        tokenSpecMap.put("FOR" ,6);
        tokenSpecMap.put("READ",7);
        tokenSpecMap.put("WRITE",8);
        tokenSpecMap.put("TO",9);
        tokenSpecMap.put("DO",10);
        tokenSpecMap.put(";" ,11);
        tokenSpecMap.put(":=",12);
        tokenSpecMap.put("+",13);
        tokenSpecMap.put("," ,14);
        tokenSpecMap.put("(",15);
        tokenSpecMap.put(")",16);
        tokenSpecMap.put("id",17);
        tokenSpecMap.put("*",18);
        tokenSpecMap.put("-",19);
        tokenSpecMap.put("DIV",20);

    }

    public String getTspec (String token){
        if(isID(token)){
            return token;
        }
        return null;
    }

    public int getTtype (String token){
        if(isID(token)){
            return tokenSpecMap.get("id");
        }
        return tokenSpecMap.get(token);
    }


    private boolean isID(String token){
        if(token.equals("PROGRAM") || token.equals("VAR") || token.equals("BEGIN") || token.equals("END.") || token.equals("END") || token.equals("FOR") ||token.equals("READ") || token.equals("WRITE") || token.equals("TO") || token.equals("DO") || token.equals("+") || token.equals("*") || token.equals("(") || token.equals(")") || token.equals(":=") || token.equals(";"))
            return false;
        return true;
    }
}
