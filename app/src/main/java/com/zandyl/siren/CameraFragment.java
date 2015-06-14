package com.zandyl.siren;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by stevedavis on 15-06-13.
 */
public class CameraFragment extends Fragment implements SpellCheckerSession.SpellCheckerSessionListener{

    ImageView imageView;
    TextView ocrText;
    private SpellCheckerSession mScs;
    public CameraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final TextServicesManager tsm = (TextServicesManager) getActivity().getSystemService(
                Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, null, this, true);

        View cameraView = inflater.inflate(R.layout.fragment_camera, container, false);

        Button imageButton = (Button)cameraView.findViewById(R.id.imageButton);

        imageView = (ImageView)cameraView.findViewById(R.id.imageView);
        ocrText = (TextView)cameraView.findViewById(R.id.ocrText);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton();
            }
        });

        Button sendButton = (Button)cameraView.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButton();
            }
        });

        return cameraView;
    }

    public void sendButton(){

        final File fileToUpload = new File(GlobalConstants.imageLoc);

        Ion.with(getActivity())
                .load("https://api.idolondemand.com/1/api/async/ocrdocument/v1")
                .setMultipartParameter("apikey", GlobalConstants.idolApiKey)
                .setMultipartFile("file", null, fileToUpload)
                .asJsonObject()
                        // run a callback on completion
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // When the loop is finished, updates the notification
                        if (e != null) {
                            e.printStackTrace();
                            return;
                        }
                        if (result != null){
                            String jobID = result.get("jobID").getAsString();
                            Ion.with(getActivity())
                                    .load( "https://api.idolondemand.com/1/job/result/" + jobID)
                                    .setBodyParameter("apikey", GlobalConstants.idolApiKey)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if(e != null){
                                                e.printStackTrace();
                                            }
                                            if (result!= null){
                                                System.out.println(result);
                                                String text = result.getAsJsonArray("actions").get(0).getAsJsonObject().getAsJsonObject("result").getAsJsonArray("text_block").get(0).getAsJsonObject().get("text").getAsString();
                                                text = text.replaceAll("[^A-Za-z0-9 ,.]", "");
                                                text = text.replace("apos", "'");
                                                text = text.replace("quot", "\"");

                                                mScs.getSuggestions(new TextInfo(text), 5);

                                                ocrText.setText(text);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void imageButton(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 111);//zero can be replaced with any action code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        System.out.println("on activity result" + resultCode + " " + requestCode);
        switch(requestCode) {
            case 111:
                if(resultCode == Activity.RESULT_OK){
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    try {
                        FileOutputStream out = new FileOutputStream(GlobalConstants.imageLoc);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(imageBitmap);
                }
                break;
        }
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {

    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }
}
