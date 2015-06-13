package com.zandyl.siren;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stevedavis on 15-06-13.
 */
public class TextFragment extends Fragment {

    public TextFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.fragment_text, container, false);

        return settingView;
    }
}
