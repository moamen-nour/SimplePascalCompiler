package com.mmz.simplepascalcompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CodeGenerator {
    private FileWriter fileWriter;
    private static String assemblyCode = "";
    private String regA="";
    public CodeGenerator() {
        File assemblyCode = new File("src/assembly.txt");
        try {
            fileWriter = new FileWriter(assemblyCode);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startProgram(String progName) {
        assemblyCode = progName + " START 0" + "\nEXTRREF XREAD,XWRITE\nSTL RETADR\n";
    }

    public void defineVariables(ArrayList<String> ids) {
        for (int i = 0; i < ids.size(); i++)
            assemblyCode += ids.get(i) + " RESW 1" + "\n";
    }

    public void read(ArrayList<String> idsRead) {
        assemblyCode += "+JSUB XREAD" + "\nWORD " + idsRead.size() + "\n";
        for (int i = 0; i < idsRead.size(); i++) {
            assemblyCode += "WORD " + idsRead.get(i) + "\n";
        }
        idsRead.clear();

    }

    public void write(ArrayList<String> idsWrite) {
        assemblyCode += "+JSUB XWRITE" + "\nWORD " + idsWrite.size() + "\n";
        for (int i = 0; i < idsWrite.size(); i++) {
            assemblyCode += "WORD " + idsWrite.get(i) + "\n";
        }
        idsWrite.clear();
    }
    public void  generateAdd(String operand1, String operand2,int counter){
        if(regA.equals(operand1)){
            assemblyCode += "ADD " + operand2 + "\n";
            regA = "T"+counter;
        }
        else if(regA.equals(operand2)){
            assemblyCode += "ADD " + operand1 + "\n";
            regA = "T"+counter;
        }
        else{
            if(regA.equals(""))
                assemblyCode += "LDA " + operand1 + "\nADD " + operand2 +"\n";
            else
                assemblyCode +="STA T" + regA.charAt(1) +"\n" + "LDA " + operand1 + "\nADD " + operand2 +"\n";
            regA = "T"+counter;
        }
    }
    public void  generateMul(String operand1, String operand2,int counter){
        if(regA.equals(operand1)){
            assemblyCode += "MUL " + operand2 + "\n";
            regA = "T"+counter;
        }
        else if(regA.equals(operand2)){
            assemblyCode += "MUL " + operand1 + "\n";
            regA = "T"+counter;
        }
        else{
            if(regA.equals(""))
                assemblyCode += "LDA " + operand1 + "\nMUL " + operand2 +"\n";
            else
                assemblyCode +="STA T" + regA.charAt(1) +"\n" + "LDA " + operand1 + "\nMUL " + operand2 +"\n";
            regA = "T"+counter;
        }
    }
    public void generateDiv(String operand1, String operand2,int counter){
        if(regA.equals(operand1)){
            assemblyCode += "DIV " + operand2 + "\n";
            regA = "T"+counter;
        }
        else  {
            if(regA.equals(""))
                assemblyCode += "LDA " + operand1 + "\nDIV " + operand2 +"\n";
            else
                assemblyCode +="STA T" + regA.charAt(1) +"\n" + "LDA " + operand1 + "\nDIV " + operand2 +"\n";
            regA = "T"+counter;
        }
    }
    public void generateSub(String operand1, String operand2,int counter){
        if(regA.equals(operand1)){
            assemblyCode += "SUB " + operand2 + "\n";
            regA = "T"+counter;
        }
        else  {
            if(regA.equals(""))
                assemblyCode += "LDA " + operand1 + "\nSUB " + operand2 +"\n";
            else
                assemblyCode +="STA T" + regA.charAt(1) +"\n" + "LDA " + operand1 + "\nSUB " + operand2 +"\n";
            regA = "T"+counter;
        }
    }
    public void storeDist(String dist){
        assemblyCode += "STA " + dist + "\n";
    }
    public void end(String progName){
        assemblyCode += "END " + progName;
    }

    public void writeCode() {
        try {
            fileWriter.append(assemblyCode);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void generateFor(String start){
        assemblyCode +="LDX "+start+"\nLOOP ";
    }
    public void terminateFor(String end){
        assemblyCode+="TIX "+end+"\nJLT LOOP\n";
    }
}
