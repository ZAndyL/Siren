package com.zandyl.siren;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

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
    public final static String INPUT_KEY = "input";



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

        minputText = (EditText)textView.findViewById(R.id.inputText);

        return textView;
    }

    public void talkButton() {
        input = minputText.getText().toString();

        if (input.equals("")) {
            minputText.setError("Please enter some text");
            return;
        }

        Intent intent = new Intent(getActivity(), DisplayActivity.class);
        intent.putExtra(INPUT_KEY, input);
        startActivity(intent);

    }


}
