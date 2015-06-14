package com.zandyl.siren;

import android.app.Fragment;
import android.content.Intent;
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

        return settingView;
    }

    public void textToSpeech(String input) {
        GlobalConstants.textToString(input, getActivity());
    }

    public void clickPlayAgain(String input) {
        GlobalConstants.textToString(input, getActivity());
    }

    public void clickPlayAnother() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}
