package com.mmz.simplepascalcompiler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements CodeEditorFragment.CodeEditorCallbacks {
    final private static int REQUESTCODE_FILE = 21427;

    private ViewPager viewPager;
    private String assemblyCode;
    private TabLayout tabLayout;
    private OurPagerAdapter ourPagerAdapter;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CodeEditorFragment(this));
        fragments.add(new AssemblyCodeFragment());


        viewPager = findViewById(R.id.view_pager);
        ourPagerAdapter = new OurPagerAdapter(getSupportFragmentManager() , fragments);
        viewPager.setAdapter(ourPagerAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedFragment = ourPagerAdapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        if (resultCode == RESULT_OK)
            if (requestCode == REQUESTCODE_FILE) {
                if (data != null) {
                    String pathName = data.getData().getPath();
                    //Get relative path of data file from the sdCard directory.
                    String sdCardRelPath = pathName.substring(pathName.lastIndexOf(':') + 1);
                    if (selectedFragment instanceof CodeEditorFragment)
                        ((CodeEditorFragment)selectedFragment).readFile(sdCardRelPath);
                }
            }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatemen
        if (id == R.id.action_loadFile) {
            //open file chooser
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("text/plain");
            startActivityForResult(intent, REQUESTCODE_FILE);


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompiled(String assembly) {
        viewPager.setCurrentItem(1);
        ((AssemblyCodeFragment)selectedFragment).setAssembelledCode(assembly);
    }
}
