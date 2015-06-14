package com.zandyl.siren;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stevedavis on 15-06-13.
 */
public class CameraFragment extends Fragment{

    ImageView imageView;
    TextView ocrText;
    ArrayList<String> entities = new ArrayList<String>();
    ListView listView;

    public CameraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View cameraView = inflater.inflate(R.layout.fragment_camera, container, false);

        Button imageButton = (Button)cameraView.findViewById(R.id.imageButton);
        Button pickImageButton = (Button)cameraView.findViewById(R.id.pickImageButton);

        imageView = (ImageView)cameraView.findViewById(R.id.imageView);
        ocrText = (TextView)cameraView.findViewById(R.id.ocrText);
        listView = (ListView) cameraView.findViewById(R.id.listView);

        entities.add("Andy Liang");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entities);
        listView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton();
            }
        });
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageButton();
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

                                                ocrText.setText(text);
                                                extractEntities(text, getActivity());
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

    public void extractEntities(String input, final Context context){
        Ion.with(context)
                .load("https://api.idolondemand.com/1/api/async/extractentities/v1")
                .setBodyParameter("apikey", GlobalConstants.idolApiKey)
                .setBodyParameter("text", input)
                .setBodyParameter("unique_entities", "true")
                .setBodyParameter("entity_type", "people_eng")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            e.printStackTrace();
                        }

                        String jobID = result.get("jobID").getAsString();

                        Ion.with(context)
                                .load("https://api.idolondemand.com/1/job/result/" + jobID)
                                .setBodyParameter("apikey", GlobalConstants.idolApiKey)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        JsonArray var1 = result.getAsJsonArray("actions");
                                        System.out.println(var1);
                                        JsonObject var2 = var1.get(0).getAsJsonObject();
                                        System.out.println(var2);
                                        JsonObject var3 = var2.getAsJsonObject("result");
                                        JsonArray var4 = var3.getAsJsonArray("entities");
                                        if (var4.size() > 0) {
                                            entities.clear();
                                            for (int i = 0; i < var4.size(); i++) {
                                                JsonObject var5 = var4.get(0).getAsJsonObject();
                                                String var6 = var5.get("normalized_text").getAsString();
                                                entities.add(var6);
                                            }
                                            ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();

                                        }
                                    }
                                });
                    }
                });
    }

    public void pickImageButton(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), 123);
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
            case 123:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
    }
}
