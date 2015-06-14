package com.zandyl.siren;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
    ArrayList<String> speechesList;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    public MessagesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messagesView = inflater.inflate(R.layout.fragment_messages, container, false);

        listView = (ListView)messagesView.findViewById(R.id.saved_list);

        sharedPreferences = getActivity().getSharedPreferences("savedSpeeches", Context.MODE_PRIVATE);
        String speechesText = sharedPreferences.getString("savedSpeeches", "");

        String[] speeches = speechesText.split("/");
        speechesList = new ArrayList<String>();

        for (int i = 1; i < speeches.length; i++) {
            speechesList.add(speeches[i]);
        }
        //Toast.makeText(getActivity().getApplicationContext(), speeches[1], Toast.LENGTH_SHORT).show();

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, speechesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;
                String itemValue = (String) listView.getItemAtPosition(itemPosition);
                textToSpeech(itemValue);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItemFromPosition(i);
                return true;
            }
        });

        return messagesView;
    }

    public void textToSpeech(String itemValue) {
        GlobalConstants.textToSpeech(itemValue,getActivity());
    }

    public void removeItemFromPosition(int i) {
        final int position = i;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Delete Speech");
        alert.setMessage("Do you want to delete this speech?");

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                speechesList.remove(position);
                String speechText = "";

                for (int j = 0; j < speechesList.size(); j++) {
                    speechText = speechText + "/" + speechesList.get(j);
                }

                sharedPreferences.edit().putString("savedSpeeches", speechText).apply();

                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();

    }

}
