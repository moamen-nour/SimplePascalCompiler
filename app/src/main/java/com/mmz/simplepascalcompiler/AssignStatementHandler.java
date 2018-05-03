package com.mmz.simplepascalcompiler;

import java.util.ArrayList;
import java.util.Stack;

public class AssignStatementHandler {
    private static int counterT = 0,counterTemp=0;
    private String dist = "";
    private CodeGenerator CodeGenerator = new CodeGenerator();
    // A utility function to return precedence of a given operator
    // Higher returned value means higher precedence
    public static int Prec(String s) {
        switch (s) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "DIV":
                return 2;
        }
        return -1;
    }
    // The main method that converts given infix expression
    // to postfix expression.
    public ArrayList<String> infixToPostfix(ArrayList<String> stringList, String dist) {
        this.dist = dist;
        // initializing empty String for result
        ArrayList<String> result = new ArrayList<String>();

        // initializing empty stack
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < stringList.size(); ++i) {
            String s = stringList.get(i);

            // If the scanned character is an operand, add it to output.
            if (!s.equals("(") && !s.equals(")") && !s.equals("+") && !s.equals("-") && !s.equals("*")
                    && !s.equals("DIV"))
                result.add(s);

                // If the scanned character is an '(', push it to the stack.
            else if (s.equals("("))
                stack.push(s);

                // If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
            else if (s.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("("))
                    result.add(stack.pop());

                // if (!stack.isEmpty() && !stack.peek().equals("("))
                // // invalid expression
                // else
                stack.pop();
            } else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(s) <= Prec(stack.peek()))
                    result.add(stack.pop());
                stack.push(s);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty())
            result.add(stack.pop());

        evaluate(result);
        return result;

    }
    //Method that uses the array list produced by the infix to postfix converter method.
    //Evaluates the expression and calls the appropriate code generation methods.
    public void evaluate(ArrayList<String> result) {
        int index = 0;
        while (result.size() != 1) {
            if (result.get(index).equals("+")) {
                CodeGenerator.generateAdd(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;

            } else if (result.get(index).equals("*")) {
                CodeGenerator.generateMul(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;
            } else if (result.get(index).equals("-")) {
                CodeGenerator.generateSub(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;
            } else if (result.get(index).equals("DIV")) {
                CodeGenerator.generateDiv(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;
            } else
                index++;
        }
        CodeGenerator.storeDist(dist);
    }
}
