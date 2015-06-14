package com.zandyl.siren;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

/**
 * Created by greencap on 6/13/2015.
 */
public class GlobalConstants {

    public static String idolApiKey = "af5e6d04-603a-4478-95aa-ac47cbb199b6";
    public static String mp3Loc = "/sdcard/test.mp3";
    public static String imageLoc = "/sdcard/pic.png";

    public static void textToSpeech(String input, Context context) {
        final Context safeContext = context;
        Ion.with(safeContext)
                .load("https://montanaflynn-text-to-speech.p.mashape.com/speak")
                .setHeader("X-Mashape-Key", "s18BGdQkJJmshz0FiUHRWGFJW7CLp1e5djojsnPLRnAvb2RAfi")
                .setBodyParameter("text", input)
                .write(new File(GlobalConstants.mp3Loc))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
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
