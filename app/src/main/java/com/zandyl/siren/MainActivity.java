package com.zandyl.siren;

import android.app.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.ibm.mobile.services.core.IBMBluemix;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {

    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button talkButton = (Button)findViewById(R.id.Talk);
        talkButton.setOnClickListener(talkButtonListener);

        inputText = (EditText)findViewById(R.id.inputText);
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

    private View.OnClickListener talkButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            talkButton();
        }
    };

    public void talkButton() {
        Toast.makeText(getApplicationContext(), "download started", Toast.LENGTH_SHORT).show();

        String input = inputText.getText().toString();
        System.out.println(input);

        //bluemix initialization
        //IBMBluemix.initialize(MainActivity.this, "com.zandyl.siren", "303dfdef881587b4d0e3f4db9166fa83ba0f0002", "http://siren.mybluemix.net");

        Toast.makeText(getApplicationContext(),"download started", Toast.LENGTH_SHORT).show();
//        Ion.with(getApplicationContext())
//                .load("http://tts-api.com/tts.mp3?q=hello+world.")
//                .write(new File("/sdcard/test.mp3"))
//                .setCallback(new FutureCallback<File>() {
//                    @Override
//                    public void onCompleted(Exception e, File file) {
//                        Toast.makeText(getApplicationContext(), "download completed", Toast.LENGTH_SHORT).show();
//
//                        if (e != null) {
//                            e.printStackTrace();
//                        }
//                        if (file == null) {
//                            System.out.println("file is null");
//                        }
//
//                        MediaPlayer mediaPlayer = new MediaPlayer();
//                        try {
//                            mediaPlayer.setDataSource(file.getPath());
//                            mediaPlayer.prepare();
//                            mediaPlayer.start();
//                            System.out.println("should be playing");
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                });



    }
}
