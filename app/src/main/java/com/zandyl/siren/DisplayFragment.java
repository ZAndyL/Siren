package com.zandyl.siren;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

/**
 * Created by uzairhaq on 15-06-13.
 */
public class DisplayFragment extends Fragment {

    TextView inputLabel;
    Button playAgain, playAnother, addToLibrary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.display_text, container, false);

        String input = getArguments().getString("input");

        inputLabel = (TextView)settingView.findViewById(R.id.inputText);
        inputLabel.setText(input);
        final String formattedInput = input.replace(' ', '+');

        textToSpeech(formattedInput);

        playAgain = (Button)settingView.findViewById(R.id.play_again);
        playAnother = (Button)settingView.findViewById(R.id.play_another);
        addToLibrary = (Button)settingView.findViewById(R.id.add_to_library);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPlayAgain(formattedInput);
            }
        });

        playAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPlayAnother();
            }
        });

        return settingView;
    }

    public void textToSpeech(String formattedInput) {

        Ion.with(getActivity().getApplicationContext())
                .load("http://tts-api.com/tts.mp3?q="+ formattedInput)
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

    public void clickPlayAgain(String formattedInput) {
        Ion.with(getActivity().getApplicationContext())
                .load("http://tts-api.com/tts.mp3?q="+ formattedInput)
                .write(new File("/sdcard/test.mp3"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        //Toast.makeText(getActivity().getApplicationContext(), "download completed", Toast.LENGTH_SHORT).show();

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

    public void clickPlayAnother() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}
