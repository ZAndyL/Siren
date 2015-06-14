package com.zandyl.siren;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

    public void textToSpeech(String input) {
        GlobalConstants.textToSpeech(input, getActivity());
    }

    public void clickPlayAgain(String input) {
        GlobalConstants.textToSpeech(input, getActivity());
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
