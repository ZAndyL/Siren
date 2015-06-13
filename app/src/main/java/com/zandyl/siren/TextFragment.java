package com.zandyl.siren;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
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
        View textView = inflater.inflate(R.layout.fragment_text, container, false);

        Button talkButton = (Button)textView.findViewById(R.id.Talk);
        talkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talkButton();
            }
        });

        Button hearButton = (Button)textView.findViewById(R.id.hearButton);
        hearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hearButton();
            }
        });
        minputText = (EditText)textView.findViewById(R.id.inputText);
        return textView;
    }

    public void hearButton() {
        final File fileToUpload = new File("/sdcard/test.mp3");
        Ion.with(getActivity())
                .load("https://api.idolondemand.com/1/api/async/recognizespeech/v1")
                .setMultipartParameter("apikey", "af5e6d04-603a-4478-95aa-ac47cbb199b6")
                .setMultipartFile("file", null, fileToUpload)
                .asJsonObject()
                        // run a callback on completion
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // When the loop is finished, updates the notification
                        Toast.makeText(getActivity(), "uploaded", Toast.LENGTH_SHORT).show();
                        if (e != null) {
                            Toast.makeText(getActivity(), "Error uploading file", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return;
                        }
                        Toast.makeText(getActivity(), "File upload complete", Toast.LENGTH_LONG).show();
                        if (result != null){
                            System.out.println("hi" + result);
                        }
                    }
                });
    }

    public void talkButton() {
        Toast.makeText(getActivity().getApplicationContext(), "download started", Toast.LENGTH_SHORT).show();
        input = minputText.getText().toString();
        String formattedInput = input.replace(' ', '+');
        Toast.makeText(getActivity().getApplicationContext(),"download started", Toast.LENGTH_SHORT).show();

        //Toast.makeText(getActivity().getApplicationContext(), formattedInput, Toast.LENGTH_SHORT).show();

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
}
