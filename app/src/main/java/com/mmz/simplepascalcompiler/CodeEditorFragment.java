package com.mmz.simplepascalcompiler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class CodeEditorFragment extends Fragment  {
    private FloatingActionButton fab;
    private EditText etCode;
    CodeEditorFragment.CodeEditorCallbacks callbacks;
    public CodeEditorFragment(CodeEditorCallbacks callbacks){
        this.callbacks = callbacks;
    }
    public interface CodeEditorCallbacks{
        void onCompiled(String assembly);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_editor_layout,container,false);

        etCode = (EditText) view.findViewById(R.id.et_code);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
                if(etCode.getText().toString().equals("")){
                    Toast.makeText(getContext(), "No Code Error, start Coding first !", Toast.LENGTH_LONG).show();
                }
                else {
                    ArrayList<Token> tokens = lexicalAnalyzer.tokenize(etCode.getText().toString());
                    if(tokens.isEmpty()){
                        Toast.makeText(getContext(), "Syntax Error, use Pascal Grammar !", Toast.LENGTH_LONG).show();
                    }
                    else {
                        SyntaticAnalyzer syntaticAnalyzer = new SyntaticAnalyzer(tokens);
                        boolean parse = syntaticAnalyzer.Parse();
                        if (parse) {
                            callbacks.onCompiled(syntaticAnalyzer.getAssemblyCode());
                            AssignStatementHandler.counterT = 0;

                        } else {
                            Toast.makeText(getContext(), "Compilation Error, check your code !", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    public void setEtCode(String string){
        etCode.setText(string);
    }

}
