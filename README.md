# SimplePascalCompiler
## About
A college project "SimplePascalCompiler",
developing the project as an android application using java.
## Stage 1 Complete : **Lexical Analysis**
Lexical analysis is the first phase of a compiler. 
It takes the modified source code from language preprocessors that are written in the form of sentences. 
The lexical analyzer breaks these syntaxes into a series of tokens, by removing any whitespace or comments in the source code.
>You can read more information about the Lexical Analysis in Compiler Design Theory from [here](https://www.tutorialspoint.com/compiler_design/compiler_design_lexical_analysis.htm)
### Our Implemnetation of a Lexical Analysier.
A lexical analysier objective can be simplified by the below image. 
![Image of Lexical Analyiser](http://quex.sourceforge.net/images/lexical-analysis-process.png)
We implemneted a Lexical Analysier Object that takes the string of Pascal Langauge Code and analyzes it acordding to teh atble of tokens.
Then,returning an arraylist of tokens that define the charachters and symbols in the input pascal code.
Moreover, We implemented a Token Class that have 2 attributes a tokenType and a tokenSpecifier. 
A TokensHandler Class is responsible for assigning this attributes to each token created by Lexical Analysier using the stored token table.
## Stage 2 Complete : **Recursive Descent Parser**
In computer science, a recursive descent parser is a kind of top-down parser built from a set of mutually recursive procedures (or a non-recursive equivalent) ,
where each such procedure usually implements one of the productions of the grammar.
>You can read more information about the Recursive Descent Parser in Compiler Design Theory from [here](https://en.wikipedia.org/wiki/Recursive_descent_parser)
#### Our Implementation of a Recursive Descent Parser
A sample parse tree for Pascal Grammer.
![Image of Parser](http://harvey.binghamton.edu/~head/CS471/NOTES/GRAMMAR/ParseTree.gif)
We implemented a Syntatic Analyser( another noatation for a parser ) Object that takes the output arrayList of Tokens objects from the Lexical Analysier.
The Syntatic Analyser then calls a bunch of iterative methods in a recursive way following the tokens one by one and removing each when recoginzing them in a specifice order according the Simplified pascal Grammer we are following.
The Grammer is shown below for reference.
```
<prog> ::=  PROGRAM  <prog-name>  VAR <id-list> BEGIN <stmt-list> END.  
<prog-name> ::=  id  
<id-list> ::=  id | <id-list>, id  
<stmt-list> ::=  <stmt> | <stmt-list> ; <stmt> 
<stmt> ::=  <assign> | <read>  | <write> | <for>  
<assign> ::=  id := <exp>  
<exp> ::= <factor> + <factor> | <factor> * factor>  | <factor> - <factor> | <factor> DIV <factor>
<factor> ::= id | ( <exp> )  
<read> ::= READ ( <id-list> ) 
<write> ::= WRITE ( <id-list> )
<for> ::= FOR <index-exp> DO <body>  
<index-exp> ::= id  := <exp> to <exp>  
<body> ::= <stmt>   |  BEGIN <stmt-list> END
```
Finally, the output of the Syntatic Analysier object is simply a boolean variable which is true if and only if the synatx of the whole code input is correct from top to bottom.
Otherwise, the boolean variable returnes is false. 
The Android Application is adjusted so that it responds to that boolean variable and do one of 2 actions based on value.
## Stage 3 Complete : Code generation for the source code into assembly code.
In computing, code generation is the process by which a compiler's code generator converts some intermediate representation of source code into a form (e.g., machine code or assembly code to be converted to machine code) that can be readily executed by a machine.
A code generator objective can be simplified by the below image. 
![Image of code generator](http://www.cs.uni.edu/~wallingf/blog-images/computing/code-generator-direct.png)
>You can read more about Code generation in Compiler Design Theory from [here](https://www.tutorialspoint.com/compiler_design/compiler_design_code_generation.htm)
### Our Implementation of a Code Generator 
We implemented a CodeGenerator Class but performed a twitch in how it works when compared to a normal CodeGenerator object. 
A normal CodeGenerator would take as input a Syntax Parse Tree which is the output of a normal Parser. 
Instead of implementing a syntax parse tree and then passing it to the code generator, we divised a method such that everytime the Parser Object recognizes a bunch of tokens that mean something together or in another word forms a corresponding assembly code, the CodeGenerator method for that part is called from within the Parser.
A pesudo code of the operation is shown below for better undertsanding. 
```Java
if(writeStmt) 			//a method returns boolean when a full write statment is recoginzed.
	//When this is activited, the CodeGenerator object takes place and forms the assembly corresponding code.
	codeGenerator.generateWriteStmt(variablesToWrite) 
else 
	return false 		 //otherwise, the CodeGenerator Object is not called and the function returns false.
```
And so on...
For each recognizble Pascal Code, assembly corresponding code is generated.
The Output of the CodeGenerator which is the assembly code itself in the form of a string. Notice that the string is not fully outputed unless the Parser Object fully returns true on the whole Pascal Code. 
Otherwise, the assembly code string is returned empty with the false boolean return value of the parser.
## Stage 4 Complete :  Validation is done, Android Fragments and Activites up and running. 
## Stage 5 Complete : APK Release version 1.0 ready.
## ISSUE under development : Minimum SDK used for application too high( 26 ).


