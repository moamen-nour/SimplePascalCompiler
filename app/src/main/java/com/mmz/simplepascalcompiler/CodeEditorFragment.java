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
                ArrayList<Token> tokens = lexicalAnalyzer.tokenize(etCode.getText().toString());
                SyntaticAnalyzer syntaticAnalyzer = new SyntaticAnalyzer(tokens);
                System.out.println(etCode.getText());
                boolean parse = syntaticAnalyzer.Parse();
                if (parse){
                    callbacks.onCompiled(syntaticAnalyzer.getAssemblyCode());
                    AssignStatementHandler.counterT = 0;

                }else {
                    Toast.makeText(getContext(),"Compilation Error" , Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void setEtCode(String string){
        etCode.setText(string);
    }
    public void readFile(String pathName) {

        File dirPath = Environment.getRootDirectory();

//        File dirPath = EnvironmentStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //create the file object using the file path.
        File file = new File(dirPath, "sdcard/Download/1.txt");
        file.setReadable(true);
        file.setExecutable(true);
        file.setWritable(true);
        System.out.println("result of mkdir:" + file.mkdirs());
        if (file.exists())
            etCode.append("file exists: " + true);
        if (file.isDirectory())
            etCode.append("\nfile is directory: " + true);
        System.out.println(file.setReadable(true));
        if (file.canRead())
            etCode.append("\nfile can read: " + true);
        if (file.isFile())
            etCode.append("\nfile is file: " + true);
        if (file.canExecute())
            etCode.append("\nfile can execute: " + true);

        StringBuilder code = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                code.append(line);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        etCode.append(code);


    }

    //Checks if the External Storage is ready for reading.
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
