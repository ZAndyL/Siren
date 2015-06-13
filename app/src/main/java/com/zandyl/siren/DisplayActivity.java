package com.zandyl.siren;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by uzairhaq on 15-06-13.
 */
public class DisplayActivity extends Activity{

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
    }

}
