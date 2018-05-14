package com.mmz.simplepascalcompiler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
    final private  static int REQUESTCODE_FILE = 21427;
    EditText etCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        etCode = (EditText) findViewById(R.id.et_code);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Token> tokens = lexicalAnalyzer.tokenize(etCode.getText().toString());
                System.out.println(tokens);
                SyntaticAnalyzer syntaticAnalyzer = new SyntaticAnalyzer(tokens);
                boolean parse = syntaticAnalyzer.Parse();
                System.out.println(parse);
                etCode.setText(syntaticAnalyzer.getAssemblyCode());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
            if(requestCode == REQUESTCODE_FILE){
                if(data != null){
                    String pathName = data.getData().getPath();
                    //Get relative path of data file from the sdCard directory.
                    String sdCardRelPath = pathName.substring(pathName.lastIndexOf(':')+1);
                    String hamda = readFile(sdCardRelPath);
                    etCode.setText(hamda);

                }
            }
    }
    private String readFile(String pathName){
        StringBuilder text = new StringBuilder();;
        if(isExternalStorageAvailable() && isExternalStorageReadOnly()){
            File dirPath = Environment.getExternalStorageDirectory();
            //create the file object using the file path.
            File file = new File(dirPath,"/"+pathName);
            //etCode.setText(pathName);
            if(file.exists()){
                try {
                    String line = "";

                    //etCode.append("fileName = " + file.getName() + "\n " + file.getPath() + "\n" + file.getAbsolutePath() );
                    //FileInputStream fileinputStream = new FileInputStream(file);
                    //InputStreamReader inputStreamReader = new InputStreamReader(fileinputStream);
                    //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    //StringBuilder code = new StringBuilder();
                    //Toast.makeText(this,"true",Toast.LENGTH_LONG).show();
                    FileReader fReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fReader);
                    while(!(line=bufferedReader.readLine()).equals(null)){
                        text.append(line);
                        //Toast.makeText(this,"true",Toast.LENGTH_LONG).show();
                        //etCode.setText(line+"\n");
                    }


                    bufferedReader.close();
                    //fileinputStream.close();
                    //inputStreamReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }


        }
        return text.toString();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatemen
         if (id == R.id.action_loadFile){
            //open file chooser
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("text/plain");
            startActivityForResult(intent,REQUESTCODE_FILE);


        }

        return super.onOptionsItemSelected(item);
    }
}
