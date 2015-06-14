package com.zandyl.siren;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by uzairhaq on 15-06-13.
 */
public class DisplayActivity extends ActionBarActivity{

    public final static String FRAGMENT_NUMBER = "fragment_Number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);

        Intent intent = getIntent();
        String input = intent.getStringExtra(TextFragment.INPUT_KEY);

        //Toast.makeText(this.getApplicationContext(), input, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("input", input);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DisplayFragment fragmentObject = new DisplayFragment();
        fragmentObject.setArguments(bundle);

        fragmentTransaction.add(R.id.display_content, fragmentObject);
        fragmentTransaction.commit();

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

                Intent intent = new Intent( DisplayActivity.this, MainActivity.class );
                intent.putExtra(FRAGMENT_NUMBER, position);
                startActivity(intent);
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
}
