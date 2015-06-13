package com.zandyl.siren;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stevedavis on 15-06-13.
 */
public class MessagesFragment extends Fragment{

    public MessagesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messagesView = inflater.inflate(R.layout.fragment_messages, container, false);
        return messagesView;
    }
}
