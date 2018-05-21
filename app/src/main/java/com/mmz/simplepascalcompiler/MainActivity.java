package com.mmz.simplepascalcompiler;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements CodeEditorFragment.CodeEditorCallbacks {
    final private static int REQUESTCODE_FILE = 21427;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private OurPagerAdapter ourPagerAdapter;
    private Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Created an array list of fragments to pass to the ViewPage adapter.
        ArrayList<Fragment> fragments = new ArrayList<>();
        //Initialize our view fragments and add them to the array list created.
        fragments.add(new CodeEditorFragment(this));
        fragments.add(new AssemblyCodeFragment());
        viewPager = findViewById(R.id.view_pager);
        //Created a view pager adapter to be set as the adapter for the view pager.
        ourPagerAdapter = new OurPagerAdapter(getSupportFragmentManager() , fragments);
        viewPager.setAdapter(ourPagerAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        //set the tab layout with the view pager.
        tabLayout.setupWithViewPager(viewPager);
        //Adding OnChange Listener for the viewpager to control the translation from a fragment to another.
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //un-implemented yet
            }
            @Override
            public void onPageSelected(int position) {
                //Do the translation between the fragments.
                selectedFragment = ourPagerAdapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //un-implemented yet
            }
        });
        //Set the current fragment as the first fragment on the start of the application.
        selectedFragment = ourPagerAdapter.getItem(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_loadFile) {
            performFileSearch();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_FILE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                try {
                    String s = readTextFromUri(uri);
                    viewPager.setCurrentItem(0);
                    if(selectedFragment instanceof CodeEditorFragment){
                        ((CodeEditorFragment) selectedFragment).setEtCode(s);
                    }
                    //Toast.makeText(this,s,Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onCompiled(String assembly) {
        viewPager.setCurrentItem(1);
        ((AssemblyCodeFragment)selectedFragment).setAssembledCode(assembly);
    }
    public void performFileSearch() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("text/*");
        startActivityForResult(intent, REQUESTCODE_FILE);
    }
    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line+"\n");
        }
        inputStream.close();
        reader.close();
        return stringBuilder.toString();
    }
    //Open files using storage access framework
    //The SAF makes it simple for users to browse and open documents, images,
    // and other files across all of their their preferred document storage providers.
    //The SAF includes the following: Document Provider , Client App , Picker
    //Document provider—A content provider that allows a storage service to reveal the files it manages.
    //The Android platform includes several built-in document providers, such as Downloads, Images, and Videos.
    //Client app—A custom app that invokes the ACTION_OPEN_DOCUMENT and/or ACTION_CREATE_DOCUMENT intent
    // and receives the files returned by document providers.
    //Picker—A system UI that lets users access documents from all document providers that satisfy the client app's search criteria.
    //The SAF centers around a content provider that is a subclass of the DocumentsProvider class.
    //Within a document provider, data is structured as a traditional file hierarchy.
    //In the SAF, providers and clients don't interact directly.
    //A client requests permission to interact with files (that is, to read, edit, create, or delete files).
    //The interaction starts when an application (in this example, a photo app) fires the intent ACTION_OPEN_DOCUMENT or ACTION_CREATE_DOCUMENT.
    //The intent can include filters to further refine the criteria—for example, "give me all openable files that have the 'image' MIME type."

}
