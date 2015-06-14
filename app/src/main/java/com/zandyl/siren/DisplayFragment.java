package com.zandyl.siren;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        final String input = getArguments().getString("input");

        inputLabel = (TextView)settingView.findViewById(R.id.inputText);
        inputLabel.setText(input);
        textToSpeech(input);

        playAgain = (Button)settingView.findViewById(R.id.play_again);
        playAnother = (Button)settingView.findViewById(R.id.play_another);
        addToLibrary = (Button)settingView.findViewById(R.id.add_to_library);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPlayAgain(input);
            }
        });

        playAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPlayAnother();
            }
        });

        addToLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickToAddToLibrary(input);
            }
        });

        return settingView;
    }

    public void textToSpeech(String formattedInput) {

        Ion.with(getActivity().getApplicationContext())
                .load("https://montanaflynn-text-to-speech.p.mashape.com/speak")
                .setHeader("X-Mashape-Key", "s18BGdQkJJmshz0FiUHRWGFJW7CLp1e5djojsnPLRnAvb2RAfi")
                .setBodyParameter("text", formattedInput)
                .write(new File("/sdcard/test.matroska"))
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
                .load("https://montanaflynn-text-to-speech.p.mashape.com/speak")
                .setHeader("X-Mashape-Key", "s18BGdQkJJmshz0FiUHRWGFJW7CLp1e5djojsnPLRnAvb2RAfi")
                .setBodyParameter("text", formattedInput)
                .write(new File("/sdcard/test.matroska"))
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

    public void clickPlayAnother() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public void clickToAddToLibrary(String input) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("savedSpeeches", Context.MODE_PRIVATE);
        String library;


        if (sharedPreferences.getString("savedSpeeches", "") != null) {
            library = sharedPreferences.getString("savedSpeeches", "") + "/" + input;
        } else {
            library = input;
        }

        //Toast.makeText(getActivity().getApplicationContext(), library, Toast.LENGTH_SHORT).show();
        sharedPreferences.edit().putString("savedSpeeches", library).apply();
    }
}
