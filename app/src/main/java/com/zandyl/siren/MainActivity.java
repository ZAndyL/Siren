package com.zandyl.siren;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new TextFragment());
            ft.commit();
        }

        Toast.makeText(getApplicationContext(),"download started", Toast.LENGTH_SHORT).show();
        Ion.with(getApplicationContext())
                .load("http://tts-api.com/tts.mp3?q=hello+world.")
                .write(new File("/sdcard/test.mp3"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        Toast.makeText(getApplicationContext(), "download completed", Toast.LENGTH_SHORT).show();

                        if (e != null) {
                            e.printStackTrace();
                        }
                        if (file == null) {
                            System.out.println("file is null");
                        }

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(file.getPath());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            System.out.println("should be playing");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
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
