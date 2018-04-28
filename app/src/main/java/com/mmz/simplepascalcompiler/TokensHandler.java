package com.mmz.simplepascalcompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TokensHandler {
    private HashMap <String , Integer> tokenSpecMap;

    public TokensHandler(){
        tokenSpecMap = new HashMap<>();

        File tokenSpecFile = new File("src/compilerFiles/tokenSpecFile.txt");
        try(Scanner scanner = new Scanner(tokenSpecFile);){
            while (scanner.hasNextLine()){
                tokenSpecMap.put(scanner.next() , scanner.nextInt());
            }

        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
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
