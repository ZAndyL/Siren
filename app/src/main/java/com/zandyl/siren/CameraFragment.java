package com.zandyl.siren;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.FileOutputStream;

/**
 * Created by stevedavis on 15-06-13.
 */
public class CameraFragment extends Fragment {

    ImageView imageView;
    public CameraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View cameraView = inflater.inflate(R.layout.fragment_camera, container, false);

        Button imageButton = (Button)cameraView.findViewById(R.id.imageButton);

        imageView = (ImageView)cameraView.findViewById(R.id.imageView);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton();
            }
        });

        return cameraView;
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
}
