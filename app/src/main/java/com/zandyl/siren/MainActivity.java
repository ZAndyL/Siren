package com.zandyl.siren;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

//import com.ibm.mobile.services.core.IBMBluemix;

//import com.ibm.mobile.services.core.IBMBluemix;


public class MainActivity extends ActionBarActivity {

    EditText inputText;
    ListView mDrawer;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new TextFragment());
            ft.commit();
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF01579B));

        ListView list = (ListView)findViewById(R.id.left_drawer);
        View header = (View)getLayoutInflater().inflate(R.layout.drawer_header,null);


        list.addHeaderView(header);
        Resources resources = getResources();
        list.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_activated_1, resources.getStringArray(R.array.drawer_items)));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Fragment fragment = null;

                    switch(position) {
                        case 1:
                            fragment = new TextFragment();
                            break;
                        case 2:
                            fragment = new MessagesFragment();
                            break;
                        case 3:
                            fragment = new SpeechFragment();
                            break;
                        case 4:
                            fragment = new CameraFragment();
                            break;
                        default:
                            break;
                    }

                    if (fragment != null){
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                    else {
                        // error in creating fragment
                        Log.e("MainActivity", "Error in creating fragment");
                    }

                    mDrawerLayout.closeDrawer(mDrawer);
            }
        });

        mDrawer = list;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Intent intent = getIntent();
        if (intent.getIntExtra(DisplayActivity.FRAGMENT_NUMBER, 0) > 0){
            mDrawer.setItemChecked(intent.getIntExtra(DisplayActivity.FRAGMENT_NUMBER, 0), true);

            Fragment fragment = new Fragment();
            switch(intent.getIntExtra(DisplayActivity.FRAGMENT_NUMBER, 0)) {
                case 1:
                    fragment = new TextFragment();
                    break;
                case 2:
                    fragment = new MessagesFragment();
                    break;
                case 3:
                    fragment = new SpeechFragment();
                    break;
                case 4:
                    fragment = new CameraFragment();
                    break;
                default:
                    break;
            }

            if (fragment != null){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        }
        else{
            mDrawer.setItemChecked(1, true);
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
