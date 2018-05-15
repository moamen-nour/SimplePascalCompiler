package com.mmz.simplepascalcompiler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;
//This class is assigned for handling assign source statement generator calls.
//Handles the infix to postfix expression conversion to be able to evaluate correctly with precedence.
class AssignStatementHandler {
    //counterT variable holds the available T variable to be used right now.
    public static int counterT;
    //Destination variable holds the destination ID of the assign statement.
    private String dist;
    //Created code generator object to preform calls on assign statement code generation.
    private CodeGenerator codeGenerator;
    //Constructor passes arguments to the infix to postfix method to convert.
    AssignStatementHandler(CodeGenerator codeGenerator,ArrayList<String> stringArrayList, String dist){
        this.dist = dist;
        this.codeGenerator=codeGenerator;
        counterT=0;
        infixToPostfix(stringArrayList,dist);

    }
    //A utility function to return precedence of a given operator. Higher returned value means higher precedence
    private static int Prec(String s) {
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
    //The main method that converts given infix expression to postfix expression.
    private void infixToPostfix(ArrayList<String> stringList, String dist) {
        // initializing empty String for result
        ArrayList<String> result = new ArrayList<String>();
        // initializing empty stack
        Stack<String> stack = new Stack<>();
        //For each string in the stringList input.
        for (int i = 0; i < stringList.size(); ++i) {
            String s = stringList.get(i);
            // If the scanned character is an operand, add it to output.
            if (!s.equals("(") && !s.equals(")") && !s.equals("+") && !s.equals("-") && !s.equals("*") && !s.equals("DIV"))
                result.add(s);
            // If the scanned character is an '(', push it to the stack.
            else if (s.equals("("))
                stack.push(s);
            // If the scanned character is an ')', pop and output from the stack until an '(' is encountered.
            else if (s.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("("))
                    result.add(stack.pop());
                stack.pop();
            } //else an operator is encountered
            else {
                while (!stack.isEmpty() && Prec(s) <= Prec(stack.peek()))
                    result.add(stack.pop());
                stack.push(s);
            }
        }
        // pop all the operators from the stack until empty.
        while (!stack.isEmpty())
            result.add(stack.pop());
        //call the evaluate method to handle the postfix expression afterwards when array list is ready.
        evaluate(result);
    }
    //This method gets called by infixToPostfix method, it's input is the postfix expression to be evaluated.
    private void evaluate(ArrayList<String> result) {
        int index = 0;
        while (result.size() != 1) {
            if (result.get(index).equals("+")) {
                codeGenerator.generateAdd(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;

            } else if (result.get(index).equals("*")) {
                codeGenerator.generateMul(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;
            } else if (result.get(index).equals("-")) {
                codeGenerator.generateSub(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;
            } else if (result.get(index).equals("DIV")) {
                codeGenerator.generateDiv(result.remove(index - 2), result.remove(index - 2), counterT);
                result.remove(index - 2);
                result.add(index - 2, "T" + counterT);
                counterT++;
                index = 0;
            } else
                index++;
        }
        codeGenerator.storeDist(dist);
    }
}
