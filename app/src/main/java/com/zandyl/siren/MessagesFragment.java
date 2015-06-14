package com.zandyl.siren;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

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

        alert.setTitle("More Options");
        alert.setMessage("What do you want to do with this speech?");

        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
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
        alert.setNeutralButton("Send w/ Branch", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                JSONObject stuff = new JSONObject();
                try {
                    stuff.put("message", position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Branch.getInstance(getActivity().getApplicationContext()).getContentUrl("email", stuff, new Branch.BranchLinkCreateListener() {
                    @Override
                    public void onLinkCreate(String url, BranchError error) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out my Speech");
                        intent.putExtra(Intent.EXTRA_TEXT, "I just created this speech in the Siren.\n\nSee it here:\n" + url);
                        intent.setType("text/plain");
                        startActivity(Intent.createChooser(intent, "Choose Email Client"));
                    }
                });
            }
        });
        alert.show();

    }

}
