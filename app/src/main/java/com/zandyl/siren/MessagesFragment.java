package com.zandyl.siren;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by stevedavis on 15-06-13.
 */
public class MessagesFragment extends Fragment{

    ListView listView;

    public MessagesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messagesView = inflater.inflate(R.layout.fragment_messages, container, false);

        listView = (ListView)messagesView.findViewById(R.id.saved_list);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("savedSpeeches", Context.MODE_PRIVATE);
        String speechesText = sharedPreferences.getString("savedSpeeches", "");

        if (!speechesText.equals("") || speechesText != null) {
            //Toast.makeText(getActivity().getApplicationContext(), speechesText, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getActivity().getApplicationContext(), "Nothing in the shared preference", Toast.LENGTH_SHORT).show();
        }

        String[] speeches = speechesText.split("/");
        //Toast.makeText(getActivity().getApplicationContext(), speeches[1], Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, speeches);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;
                String itemValue = (String)listView.getItemAtPosition(itemPosition);
                textToSpeech(itemValue);

            }
        });

        return messagesView;
    }

    public void textToSpeech(String itemValue) {
        Ion.with(getActivity().getApplicationContext())
                .load("http://tts-api.com/tts.mp3?q="+ itemValue)
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
