package com.zandyl.siren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

/**
 * Created by stevedavis on 15-06-13.
 */
public class SpeechFragment extends Fragment {

    public SpeechFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View speechView = inflater.inflate(R.layout.fragment_speech, container, false);
        return speechView;
    }
}
