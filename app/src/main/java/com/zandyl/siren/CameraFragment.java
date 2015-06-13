package com.zandyl.siren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

/**
 * Created by stevedavis on 15-06-13.
 */
public class CameraFragment extends Fragment {

    public CameraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cameraView = inflater.inflate(R.layout.fragment_camera, container, false);
        return cameraView;
    }

}
