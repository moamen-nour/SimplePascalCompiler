package com.mmz.simplepascalcompiler;

public class TestCompiler {
    public static void main(String[] args){
        String string = "PROGRAM BASICS\n" +
                "VAR\n" +
                "X,Y,A,B,C,Z\n" +
                "BEGIN\n" +
                "READ(X,Y,Z,B)\n" +
                "A := X+B;\n" +
                "C := X+Z;\n" +
                "C := C*B;\n" +
                "Z := A+B+C+Y;\n" +
                "WRITE(A,C,Z)\n" +
                "END\n" +
                "END.";

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        lexicalAnalyzer.tokenize(string);

    }
}
