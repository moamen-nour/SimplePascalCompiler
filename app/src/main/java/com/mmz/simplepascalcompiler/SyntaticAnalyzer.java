package com.mmz.simplepascalcompiler;
import java.util.ArrayList;
//SyntaticAnaylzer is the class responsible for handling the syntax of the source code.
//Also called Parser.
//Main Purpose is checking the source code and outputs either true or false.
//Secondary Purpose is calling the Code generator within when a single part is recognized as true.
public class SyntaticAnalyzer {
    //Array list to hold tokens returned by tokenize method.
    private ArrayList<Token> tokens;
    //Array list to hold variables defined at VAR <id-list>.
    private ArrayList<String> ids;
    //Array list to hold variables defined at READ(<id-list>).
    private ArrayList<String> idsRead;
    //Array list to hold variables defined at WRITE(<id-list>).
    private ArrayList<String> idsWrite;
    //PROGRAM progName
    private String progName = "";
    //Created code generator object to preform calls on code generation.
    private CodeGenerator codeGenerator;
    private boolean readStmt = false, writeStmt = false;
    private int counterComma = 0, counterID = 0, counterOperation = 0, loopLimit = 3;
    private String assemblyCode;
    //Constructor to initialize varaiables.
    SyntaticAnalyzer(ArrayList<Token> tokens) {
        this.codeGenerator = new CodeGenerator();
        this.tokens = tokens;
        ids = new ArrayList<>();
        idsRead = new ArrayList<>();
        idsWrite = new ArrayList<>();
    }
    //Public Procedure to interface with the Syntatic Analyser object.
    public String getAssemblyCode() {
        return assemblyCode;
    }
    //Public Procedure to interface with the Syntatic Analyser object.
    public boolean Parse() {
        if (tokens.isEmpty()) {
            return false;
        } else {
            boolean program, programName, var, idList, begin, stmtList, end = false;
            program = programKey();
            if (program) {
                programName = programName();
                if (programName) {
                    codeGenerator.startProgram(progName);
                    var = varKey();
                    if (var) {
                        idList = idList();
                        if (idList) {
                            codeGenerator.defineVariables(ids);
                            begin = beginKey();
                            if (begin) {
                                stmtList = stmtList();
                                if (stmtList) {
                                    end = endKey();
                                    if (end) {
                                        codeGenerator.end(progName);
                                        assemblyCode = codeGenerator.getCode();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return end;
        }
    }
    //Method 1 : Check Validity of PROGRAM keyword.
    private boolean programKey() {
        //Token_type =1 ---> PROGRAM keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 1) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found --> return false
        return false;
    }
    //Method 2 : Check Validity of program_name id.
    private boolean programName() {
        // Token_type = 17 ---> ID found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 17) {
                // save the programName for later use.
                progName = tokens.get(0).getTokenSpecifier();
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 3 : Check Validity of VAR keyword.
    private boolean varKey() {
        //Token_type =2 ---> VAR keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 2) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 4 : Check Validity of ID list.
    private boolean idList() {
        if (!tokens.isEmpty()) {
            while (tokens.get(0).getTokenType() != loopLimit) {
                if (tokens.get(0).getTokenType() == 17) {
                    counterID++;
                    if (ids.contains(tokens.get(0).getTokenSpecifier()) && readStmt)
                        idsRead.add(tokens.get(0).getTokenSpecifier());
                    else if (ids.contains(tokens.get(0).getTokenSpecifier()) && writeStmt)
                        idsWrite.add(tokens.get(0).getTokenSpecifier());
                    else if (!writeStmt && !readStmt)
                        ids.add(tokens.get(0).getTokenSpecifier());
                    else break;
                    tokens.remove(0);
                } else if (tokens.get(0).getTokenType() == 14) {
                    counterComma++;
                    tokens.remove(0);
                } else
                    break;
            }

            if (tokens.get(0).getTokenType() == loopLimit && counterID - counterComma == 1) {
                counterID = counterComma = 0;
                return true;
            }
        }
        return false;
    }
    //Method 5 : Check Validity of BEGIN keyword.
    private boolean beginKey() {
        //Token_type = 3 ---> BEGIN keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 3) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 6 : Check Validity of the open bracket keyword.
    private boolean openbracketKey() {
        //Token_type = 15 ---> "(" keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 15) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 7 : Check Validity of the closed bracket keyword.
    private boolean closedbracketKey() {
        //Token_type = 16 ---> "(" keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 16) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 8 : Check Validity of READ keyword.
    private boolean readKey() {
        //Token_type = 7 ---> READ keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 7) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 9 : Check Validity of WRITE keyword.
    private boolean writeKey() {
        //Token_type = 8 ---> WRITE keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 8) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 10: Check Validity of "END." keyword
    private boolean endKey() {
        //Token_type = 5 ---> END. keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 5) {
                tokens.remove(0);

                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 11: Check Validity of FOR keyword
    private boolean forKey() {
        //Token_type = 6 ---> FOR keyword found.
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() == 6) {
                tokens.remove(0);
                return true;
            }
        }
        // if not found or list of tokens is empty --> return false
        return false;
    }
    //Method 12: Check Validity of WRITE stmt.
    private boolean writeStmt() {
        writeStmt = true;
        loopLimit = 16;
        if (openbracketKey()) {
            if (idList()) {
                if (closedbracketKey()) {
                    if (!tokens.isEmpty() && (tokens.get(0).getTokenType() == 11 || tokens.get(0).getTokenType() == 5)) {
                        readStmt = false;
                        return true;
                    }
                }
            }
        }
        writeStmt = false;
        return false;
    }
    //Method 12: Check Validity of READ stmt.
    private boolean readStmt() {
        readStmt = true;
        loopLimit = 16;
        if (openbracketKey()) {
            if (idList()) {
                if (closedbracketKey()) {
                    if (!tokens.isEmpty() && (tokens.get(0).getTokenType() == 11 || tokens.get(0).getTokenType() == 5)) {
                        readStmt = false;
                        return true;
                    }
                }
            }
        }
        readStmt = false;
        return false;
    }
    //Method 13: Check Validity of FOR stmt.
    private boolean forStmt() {
        String idCounter = "", idStart = "", idEnd = "";
        // Correct Syntax --> FOR ID := ID to ID
        // Found ID to be counter for the loop.
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == 17 && ids.contains(tokens.get(0).getTokenSpecifier())) {
            idCounter = tokens.get(0).getTokenSpecifier();
            // Found ":="
            if (!tokens.isEmpty() && tokens.get(1).getTokenType() == 12) {
                // Found ID
                if (!tokens.isEmpty() && tokens.get(2).getTokenType() == 17 && ids.contains(tokens.get(0).getTokenSpecifier())) {
                    idStart = tokens.get(2).getTokenSpecifier();
                    // Found "TO"
                    if (!tokens.isEmpty() && tokens.get(3).getTokenType() == 9) {
                        //Found ID
                        if (!tokens.isEmpty() && tokens.get(4).getTokenType() == 17 && ids.contains(tokens.get(0).getTokenSpecifier())) {
                            idEnd = tokens.get(4).getTokenSpecifier();
                            // Found "DO"
                            if (!tokens.isEmpty() && tokens.get(5).getTokenType() == 10) {
                                tokens.remove(0);
                                tokens.remove(0);
                                tokens.remove(0);
                                tokens.remove(0);
                                tokens.remove(0);
                                tokens.remove(0);
                            }
                        }
                    }
                }
            }
        }
        //Multi-line Loop must start with BEGIN and end with END keywords respectively.
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == 3) {
            tokens.remove(0);
            codeGenerator.generateFor(idStart);
            if (stmtList()) {
                if (!tokens.isEmpty() && tokens.get(0).getTokenType() == 4) {
                    tokens.remove(0);
                    codeGenerator.terminateFor(idEnd);
                    return true;
                }
            }
        }
        //One-line Loop (NO BEGIN and END)
        else {
            codeGenerator.generateFor(idStart);
            if (stmt()) {
                codeGenerator.terminateFor(idEnd);
                return true;
            }
        }
        return false;
    }
    //Method 14: stmtList to check a certain stmt type according to key.
    private boolean stmtList() {
        //until "END." is reached.
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() != 5) {
            //IF the stmt is a read statement.
            if (readKey()) {
                if (!readStmt()) break;
                else codeGenerator.read(idsRead);
                //IF the stmt is a write statement.
            } else if (writeKey()) {
                if (!writeStmt()) break;
                else codeGenerator.write(idsWrite);
            } else if (forKey()) {
                if (!forStmt()) break;
            }
            //IF the stmt is an assign stmt.
            else if (tokens.get(0).getTokenType() == 17 && tokens.get(1).getTokenType() == 12) {
                String dist = tokens.get(0).getTokenSpecifier();
                tokens.remove(0);
                tokens.remove(0);
                //Check Validity of stmt
                if (!assign(dist))
                    break;
            } else if (tokens.get(0).getTokenType() == 11)
                tokens.remove(0);
            else
                break;

        }
        if (!tokens.isEmpty() && (tokens.get(0).getTokenType() == 5 || tokens.get(0).getTokenType() == 4))
            return true;

        return false;
    }
    //Method 15: stmt to handle one-line loops.
    private boolean stmt() {
        //IF the stmt is a read statement.
        if (readKey()) {
            if (!readStmt()) return false;
            else codeGenerator.read(idsRead);
            //IF the stmt is a write statement.
        } else if (writeKey()) {
            if (!writeStmt()) return false;
            else codeGenerator.write(idsWrite);

        } else if (forKey()) {
            if (!forStmt()) return false;
        }
        //IF the stmt is an assign stmt.
        else if (!tokens.isEmpty() && tokens.get(0).getTokenType() == 17 && tokens.get(1).getTokenType() == 12) {
            String dist = tokens.get(0).getTokenSpecifier();
            tokens.remove(0);
            tokens.remove(0);
            //Check Validity of stmt
            if (!assign(dist)) return false;
        }
        //The Stmt is not valid.
        else
            return false;
        return true;
    }
    //Method 16: Check Validity of assign stmt.
    private boolean assign(String dist) {
        ArrayList<String> arr = new ArrayList<String>();
        boolean flag = true;
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() != 11 && tokens.get(0).getTokenType() != 5) {
            if (tokens.get(0).getTokenType() == 17) {
                if (ids.contains(tokens.get(0).getTokenSpecifier())) {
                    arr.add(tokens.get(0).getTokenSpecifier());
                    tokens.remove(0);
                    counterID++;
                } else
                    break;
            } else if (tokens.get(0).getTokenType() == 13 || tokens.get(0).getTokenType() == 18
                    || tokens.get(0).getTokenType() == 19 || tokens.get(0).getTokenType() == 20) {
                if (tokens.get(0).getTokenType() == 13)
                    arr.add("+");
                else if (tokens.get(0).getTokenType() == 18)
                    arr.add("*");
                else if (tokens.get(0).getTokenType() == 19)
                    arr.add("-");
                else
                    arr.add("DIV");
                tokens.remove(0);
                counterOperation++;
            } else if (tokens.get(0).getTokenType() == 15) {
                arr.add("(");
                tokens.remove(0);
                if (!exp(arr))
                    break;
            } else {
                flag = false;
                break;
            }
        }
        if (flag && counterID - counterOperation == 1) {
            new AssignStatementHandler(codeGenerator, arr, dist);
            counterID = counterOperation = 0;
            return true;
        }
        return false;
    }
    //Method 17: Check Validity of an expression inside an assign stmt.
    private boolean exp(ArrayList<String> arr) {
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() != 16) {
            if (tokens.get(0).getTokenType() == 17) {
                if (ids.contains(tokens.get(0).getTokenSpecifier())) {
                    arr.add(tokens.get(0).getTokenSpecifier());
                    tokens.remove(0);
                    counterID++;
                } else
                    break;
            } else if (tokens.get(0).getTokenType() == 13 || tokens.get(0).getTokenType() == 18
                    || tokens.get(0).getTokenType() == 19 || tokens.get(0).getTokenType() == 20) {
                if (tokens.get(0).getTokenType() == 13)
                    arr.add("+");
                else if (tokens.get(0).getTokenType() == 18)
                    arr.add("*");
                else if (tokens.get(0).getTokenType() == 19)
                    arr.add("-");
                else
                    arr.add("DIV");
                counterOperation++;
                tokens.remove(0);
            } else if (tokens.get(0).getTokenType() == 15) {
                arr.add("(");
                tokens.remove(0);
                if (!exp(arr))
                    break;
            } else
                break;
        }
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == 16) {
            arr.add(")");
            tokens.remove(0);
            return true;
        }
        return false;
    }
}
