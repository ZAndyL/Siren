package com.zandyl.siren;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

/**
 * Created by stevedavis on 15-06-13.
 */
public class TextFragment extends Fragment {

    EditText minputText;
    String input;

    public TextFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.fragment_text, container, false);

        Button talkButton = (Button)settingView.findViewById(R.id.Talk);
        talkButton.setOnClickListener(talkButtonListener);

        minputText = (EditText)settingView.findViewById(R.id.inputText);

        return settingView;
    }

    private View.OnClickListener talkButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            talkButton();
        }
    };

    public void talkButton() {
        //bluemix initialization
        //IBMBluemix.initialize(MainActivity.this, "com.zandyl.siren", "303dfdef881587b4d0e3f4db9166fa83ba0f0002", "http://siren.mybluemix.net");

        System.out.println("Hello World");

        Toast.makeText(getActivity().getApplicationContext(),"download started", Toast.LENGTH_SHORT).show();
        Ion.with(getActivity().getApplicationContext())
                .load("http://tts-api.com/tts.mp3?q=hello+world.")
                .write(new File("/sdcard/test.mp3"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        Toast.makeText(getActivity().getApplicationContext(), "download completed", Toast.LENGTH_SHORT).show();

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
}
